package ac.yongson.artgram.domain.comment.repository;

import ac.yongson.artgram.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
