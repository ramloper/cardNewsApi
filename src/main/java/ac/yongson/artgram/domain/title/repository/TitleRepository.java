package ac.yongson.artgram.domain.title.repository;

import ac.yongson.artgram.domain.title.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long> {
}
