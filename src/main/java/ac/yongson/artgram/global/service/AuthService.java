package ac.yongson.artgram.global.service;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    Long getLoginMemberId(HttpServletRequest request);
}
