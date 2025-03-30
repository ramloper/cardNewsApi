package ac.yongson.artgram.domain.member.service;

import ac.yongson.artgram.domain.image.entity.Image;
import ac.yongson.artgram.domain.image.service.ImageService;
import ac.yongson.artgram.domain.member.dto.MemberRequestDTO;
import ac.yongson.artgram.domain.member.dto.MemberResponseDTO;
import ac.yongson.artgram.domain.member.dto.TokenDTO;
import ac.yongson.artgram.domain.member.entity.Member;
import ac.yongson.artgram.domain.member.repository.MemberQueryRepository;
import ac.yongson.artgram.domain.member.repository.MemberRepository;
import ac.yongson.artgram.global.auth.JwtTokenProvider;
import ac.yongson.artgram.global.exception.Exception400;
import ac.yongson.artgram.global.exception.Exception401;
import ac.yongson.artgram.global.exception.Exception404;
import ac.yongson.artgram.global.exception.Exception500;
import ac.yongson.artgram.global.service.RedisService;
import ac.yongson.artgram.global.service.S3ImageService;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final ImageService imageService;
    @Override
    @Transactional
    public TokenDTO login(MemberRequestDTO.Login loginDTO) {
        Member memberPS = getMemberByStudentId(loginDTO.getStudentId());
        passwordCheck(loginDTO.getPassword(),memberPS.getPassword());
        isSubscription(memberPS);
        final String accessToken = jwtTokenProvider.createAccessToken(memberPS);
        final String refreshToken = jwtTokenProvider.createRefreshToken(memberPS);
        redisService.setValues(refreshToken,accessToken);
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public HttpHeaders setRefreshToken(MemberRequestDTO.Login login, TokenDTO tokenDTO, HttpServletResponse response) {
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", tokenDTO.getRefreshToken())
                .path("/")
                .sameSite("None")
                .maxAge(1000 * 60 * 60 * 24 * 14)
                .secure(true) // https로 바꾸면 true 변경
                .httpOnly(true) // 나중에 true로 변경
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());
        return headers;
    }

    @Override
    public HttpHeaders logout(String refreshToken) {
        if(!redisService.existsRefreshToken(refreshToken)) throw new Exception404("refreshToken을 찾을 수 없습니다.");
        redisService.deleteValues(refreshToken);
        HttpHeaders headers = new HttpHeaders();
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", "")
                .path("/")
                .sameSite("None")
                .maxAge(1000 * 60 * 60 * 24 * 14)
                .secure(true) // https로 바꾸면 true 변경
                .httpOnly(true) // 나중에 true로 변경
                .build();
        headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());
        return headers;
    }

    @Override
    public String reCreateAccessToken(String refreshToken) {
        if(refreshToken == null) throw new Exception401("RefreshToken 유효하지 않음, 재로그인 요청");
        if (redisService.existsRefreshToken(refreshToken)) {
            DecodedJWT decodedJWT = JwtTokenProvider.verify(refreshToken);
            Long id = decodedJWT.getClaim("id").asLong();
            Member memberPS = memberRepository.findById(id).orElseThrow();
            return jwtTokenProvider.recreationAccessToken(memberPS);
        } else {
            throw new Exception401("RefreshToken 유효하지 않음, 재로그인 요청");
        }
    }
    @Override
    @Transactional
    public void registerMember(MemberRequestDTO.RegisterMember registerMember, MultipartFile profileImage){
        isMemberStudentIdCheck(registerMember.getStudentId());
        Image image = imageService.registerImage(profileImage);
        try{
            memberRepository.save(registerMember.toEntity(image));
        }catch (Exception e){
            imageService.deleteImage(image);
            throw new Exception400("member", "회원가입 실패");
        }
    }
    @Override
    public MemberResponseDTO.MemberSimpleInfo getMemberSimpleInfo(Long memberId){
        return memberQueryRepository.getMemberSimpleInfo(memberId);
    }

    @Override
    public List<MemberResponseDTO.WaitingToMember> getWaitingToJoin(){
        return memberQueryRepository.getWaitingToMembers();
    }
    @Override
    @Transactional
    public void patchWaitingToJoin(Long memberId){
        Member memberPS = memberRepository.findById(memberId).orElseThrow(() -> new Exception400("member", "해당 학생이 없습니다."));
        try{
            memberPS.approved();
        }catch (Exception e){
            throw new Exception500("member", "가입 승인 실패");
        }

    }
    private void isMemberStudentIdCheck(String studentId) {
        Optional<Member> member = memberRepository.findByStudentId(studentId);
        if(member.isPresent()) throw new Exception400("member","중복된 학번입니다.");
    }
    private void isSubscription(Member memberPS){
        if(!memberPS.isSubscription()){
            throw new Exception400("member", "가입승인 대기 상태입니다.");
        }
    }
    private Member getMemberByStudentId(String studentId){
        return memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new Exception400("member", "해당 학번을 가진 회원을 찾을 수 없습니다."));
    }
    private void passwordCheck(String requestPassword, String persistencePassword) {
        if (!passwordEncoder.matches(requestPassword, persistencePassword)) {
            throw new Exception400("member", "잘못된 비밀번호입니다.");
        }
    }
}
