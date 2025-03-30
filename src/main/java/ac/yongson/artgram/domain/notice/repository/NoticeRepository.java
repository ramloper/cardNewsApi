package ac.yongson.artgram.domain.notice.repository;

import ac.yongson.artgram.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
