package ac.yongson.artgram.global.service.impl;

import ac.yongson.artgram.global.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public Long getLoginMemberId(HttpServletRequest request){
        return (Long) request.getAttribute("memberId");
    }

}
