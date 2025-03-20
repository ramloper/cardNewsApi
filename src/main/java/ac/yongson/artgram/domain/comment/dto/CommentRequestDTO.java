package ac.yongson.artgram.domain.comment.dto;

import ac.yongson.artgram.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentRequestDTO {
    @Getter
    public static class SaveComment{
        private Long boardId;
        private String content;

        public Comment toEntity(Long memberId){
            return Comment.builder()
                    .content(content)
                    .memberId(memberId)
                    .boardId(boardId)
                    .build();
        }
    }
}
