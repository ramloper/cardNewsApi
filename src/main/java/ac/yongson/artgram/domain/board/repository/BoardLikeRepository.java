package ac.yongson.artgram.domain.board.repository;

import ac.yongson.artgram.domain.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    BoardLike findByMemberIdAndBoardId(Long memberId, Long boardId);
}
