package ac.yongson.artgram.global.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
@RequiredArgsConstructor
@Component
public class RedisService {
    private final RedisTemplate redisTemplate;

    public void setValues(String refreshToken, String accessToken) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(refreshToken, accessToken, Duration.ofMinutes(1000 * 60 * 30L)); // 7days
    }

    public String getValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    public boolean existsRefreshToken(String refreshToken) {
        return getValues(refreshToken) != null;
    }

}
