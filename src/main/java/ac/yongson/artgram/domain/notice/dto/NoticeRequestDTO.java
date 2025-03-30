package ac.yongson.artgram.domain.notice.dto;

import ac.yongson.artgram.domain.notice.entity.Notice;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class NoticeRequestDTO {

    @Getter
    public static class SaveNotice{
        @NotEmpty
        private String title;
        @NotEmpty
        private String content;

        public Notice toEntity(){
            return Notice.builder()
                    .title(title)
                    .content(content)
                    .build();
        }
    }
}
