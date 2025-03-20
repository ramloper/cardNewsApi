package ac.yongson.artgram.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class BoardResponseDTO {

    @Getter
    public static class Post {
        private Long boardId;
        private String title;
        private String content;
        private String memberName;
        private String profileImageUrl;
        private int likeCount;
        private Boolean isLike = false;
        private Integer commentCount;
        private List<Long> imageIds;
        public void isLike(){
            this.isLike = true;
        }
    }

    @Getter
    @Setter
    public static class BoardInfo {
        private Long boardId;
        private String title;
        private String content;
        private String boardCreateName;
        private String boardCreateProfileImageUrl;
        private int likeCount;
        private Boolean isLike = false;
        private List<CommentInfo> comments;
        private List<String> images;
        public void isLike(){
            this.isLike = true;
        }
        @Getter
        public static class CommentInfo {
            private Long commentId;
            private String commentCreateName;
            private String commentCreateProfileImageUrl;
            private String content;
            private ZonedDateTime commentCreateTime;
        }
    }
}
