package ac.yongson.artgram.global.auth;

import ac.yongson.artgram.domain.member.entity.Member;
import ac.yongson.artgram.global.dto.ResponseDTO;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtTokenAuthFilter extends BasicAuthenticationFilter {
    public JwtTokenAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String prefixJwt = request.getHeader(JwtTokenProvider.HEADER);
        if (prefixJwt == null) {
            chain.doFilter(request, response);
            return;
        }
        String jwt = prefixJwt.replaceAll(JwtTokenProvider.TOKEN_PREFIX, "");
        try {
            DecodedJWT decodedJWT = JwtTokenProvider.verify(jwt);
            Long id = decodedJWT.getClaim("id").asLong();
            String role = decodedJWT.getClaim("role").asString();
            Member member = Member.builder().memberId(id).memberRole(Member.MemberRole.valueOf(role)).build();
            MyUserDetails myUserDetails = new MyUserDetails(member);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            myUserDetails,
                            myUserDetails.getPassword(),
                            myUserDetails.getAuthorities()
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.setAttribute("memberId", id);
        } catch (SignatureVerificationException | JWTDecodeException sve) {
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "토큰 검증 실패", "토큰 검증 실패");
            log.warn("인증되지 않은 사용자가 자원에 접근하려 합니다.");
        } catch (TokenExpiredException tee) {
            log.warn("만료된 토큰");
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "토큰 만료됨", "토큰 만료됨");
        }finally {
            chain.doFilter(request, response);
        }
    }

    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message, String detail) throws IOException {
        ResponseDTO<String> responseBody = new ResponseDTO<>(status, message, detail);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(401);
        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}