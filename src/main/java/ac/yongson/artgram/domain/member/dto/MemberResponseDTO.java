package ac.yongson.artgram.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberResponseDTO {
    @Getter
    public static class MemberSimpleInfo{
        private String studentId;
        private String memberName;
        private String profileImageUrl;
    }

    @Getter
    public static class WaitingToMember{
        private Long memberId;
        private String studentId;
        private String memberName;
    }
}
