package ac.yongson.artgram.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
}
