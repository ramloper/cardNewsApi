package ac.yongson.artgram.domain.board.dto;

import ac.yongson.artgram.domain.board.entity.Board;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class BoardRequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SaveBoard {
        @NotEmpty
        private String title;
        @NotEmpty
        private String content;

        public Board toEntity(Long memberId){
            return Board.builder()
                    .title(title)
                    .content(content)
                    .memberId(memberId)
                    .build();
        }
    }
}
