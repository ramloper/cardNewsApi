package ac.yongson.artgram.domain.member.controller;

import ac.yongson.artgram.domain.member.dto.MemberRequestDTO;
import ac.yongson.artgram.domain.member.dto.TokenDTO;
import ac.yongson.artgram.domain.member.service.MemberService;
import ac.yongson.artgram.global.dto.ResponseDTO;
import ac.yongson.artgram.global.service.AuthService;
import ac.yongson.artgram.global.service.S3ImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberRequestDTO.Login login, HttpServletResponse response) {
        TokenDTO tokenDTO = memberService.login(login);
        return ResponseEntity.ok()
                .headers(memberService.setRefreshToken(login,tokenDTO,response))
                .body(new ResponseDTO<>(tokenDTO.getAccessToken()));
    }
    @PutMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "refreshToken",required = false) String refreshToken, HttpServletRequest request) {
        if(refreshToken == null){
            refreshToken = request.getHeader("refreshToken");
        }
        return ResponseEntity.ok()
                .headers(memberService.logout(refreshToken))
                .body(new ResponseDTO<>("로그아웃 성공"));
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> recreationAccessToken(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletRequest request) {
        if(refreshToken == null){
            refreshToken = request.getHeader("refreshToken");
        }
        String accessToken = memberService.reCreateAccessToken(refreshToken);
        return ResponseEntity.ok()
                .body(new ResponseDTO<>(accessToken));
    }
    @PostMapping("member")
    public ResponseEntity<?> join(@ModelAttribute MemberRequestDTO.RegisterMember registerMember,
                                  @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
                                  HttpServletRequest request) {

        memberService.registerMember(registerMember, profileImage);
        return ResponseEntity.ok().body(new ResponseDTO<>("회원가입 성공"));
    }
    @GetMapping("auth/member")
    public ResponseEntity<?> getMemberSimpleInfo(HttpServletRequest request) {
        Long memberId = authService.getLoginMemberId(request);
        return ResponseEntity.ok().body(new ResponseDTO<>(memberService.getMemberSimpleInfo(memberId)));
    }

    @GetMapping("admin/waiting/join")
    public ResponseEntity<?> getWaitingToJoin() {
        return ResponseEntity.ok().body(new ResponseDTO<>(memberService.getWaitingToJoin()));
    }

    @PatchMapping("admin/waiting/join/{memberId}")
    public ResponseEntity<?> patchWaitingToJoin(@PathVariable Long memberId) {
        memberService.patchWaitingToJoin(memberId);
        return ResponseEntity.ok().body(new ResponseDTO<>("승인 성공"));
    }
}
