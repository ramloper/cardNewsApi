package ac.yongson.artgram.domain.member.dto;

import ac.yongson.artgram.domain.image.entity.Image;
import ac.yongson.artgram.domain.member.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
public class MemberRequestDTO {

    @Getter
    public static class Login {
        @NotEmpty
        private String studentId;
        @NotEmpty
        private String memberName;
        @NotEmpty
        private String password;
    }

    @Getter
    @Setter
    public static class RegisterMember {
        private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        @NotEmpty
        private String studentId;
        @NotEmpty
        private String password;
        @NotEmpty
        private String memberName;
        public Member toEntity(Image image){
            return Member.builder()
                    .studentId(studentId)
                    .password(passwordEncoder.encode(password))
                    .memberName(memberName)
                    .memberRole(Member.MemberRole.USER)
                    .image(image)
                    .isSubscription(false)
                    .build();
        }
    }
}
