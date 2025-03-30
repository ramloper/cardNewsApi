package ac.yongson.artgram.domain.board.repository;

import ac.yongson.artgram.domain.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    BoardLike findByMemberIdAndBoardId(Long memberId, Long boardId);

    @Query("select bl.boardId from BoardLike bl where bl.memberId = :memberId order by bl.createdDateTime desc")
    List<Long> findAllByMemberId(@Param("memberId") Long memberId);
}
