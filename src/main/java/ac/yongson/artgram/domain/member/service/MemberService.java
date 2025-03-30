package ac.yongson.artgram.domain.member.service;

import ac.yongson.artgram.domain.member.dto.MemberRequestDTO;
import ac.yongson.artgram.domain.member.dto.MemberResponseDTO;
import ac.yongson.artgram.domain.member.dto.TokenDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MemberService {
    TokenDTO login(MemberRequestDTO.Login request);
    HttpHeaders setRefreshToken(MemberRequestDTO.Login login,TokenDTO tokenDTO, HttpServletResponse response);
    HttpHeaders logout(String refreshToken);
    String reCreateAccessToken(String refreshToken);
    void registerMember(MemberRequestDTO.RegisterMember registerMember, MultipartFile profileImage);
    MemberResponseDTO.MemberSimpleInfo getMemberSimpleInfo(Long memberId);
    List<MemberResponseDTO.WaitingToMember> getWaitingToJoin();
    void patchWaitingToJoin(Long memberId);
}
