package ac.yongson.artgram.domain.image.repository;

import ac.yongson.artgram.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
