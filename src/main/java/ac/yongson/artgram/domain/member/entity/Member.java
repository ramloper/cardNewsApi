package ac.yongson.artgram.domain.member.entity;

import ac.yongson.artgram.domain.board.entity.Board;
import ac.yongson.artgram.domain.image.entity.Image;
import ac.yongson.artgram.global.timestamp.Timestamped;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private String studentId;
    private String password;
    private String memberName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;
    private boolean isSubscription;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Board> boards = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public enum MemberRole {
        USER("USER"),
        ADMIN("ADMIN");

        private final String memberRoleName;

    }

    public void approved(){
        this.isSubscription = true;
    }
}
