package ac.yongson.artgram.domain.board.entity;

import ac.yongson.artgram.domain.member.entity.Member;
import ac.yongson.artgram.global.timestamp.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board_like") // 테이블명 명시적 지정
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BoardLike extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_like_id")
    private Long boardLikeId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @Column(name = "board_id", nullable = false)
    private Long boardId;
    @ManyToOne
    @JoinColumn(name = "board_id", insertable = false, updatable = false)
    private Board board;
}
