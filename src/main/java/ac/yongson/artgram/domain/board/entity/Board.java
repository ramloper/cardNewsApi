package ac.yongson.artgram.domain.board.entity;

import ac.yongson.artgram.domain.comment.entity.Comment;
import ac.yongson.artgram.domain.image.entity.Image;
import ac.yongson.artgram.domain.member.entity.Member;
import ac.yongson.artgram.global.timestamp.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board") // 테이블명 명시적 지정
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BoardLinkImage> boardImages = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    private int likeCount;

    public void incrementLikeCount() {
        this.likeCount++;
    }
    public void decrementLikeCount() {this.likeCount--;}
}
