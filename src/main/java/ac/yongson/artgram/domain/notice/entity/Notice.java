package ac.yongson.artgram.domain.notice.entity;

import ac.yongson.artgram.global.timestamp.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notice") // 테이블명 명시적 지정
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Notice extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
}
