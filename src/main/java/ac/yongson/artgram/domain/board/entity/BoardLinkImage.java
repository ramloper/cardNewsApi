package ac.yongson.artgram.domain.board.entity;

import ac.yongson.artgram.domain.image.entity.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board_link_image")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BoardLinkImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardLinkImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;
}
