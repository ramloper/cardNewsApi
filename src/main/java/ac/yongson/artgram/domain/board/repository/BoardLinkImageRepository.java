package ac.yongson.artgram.domain.board.repository;

import ac.yongson.artgram.domain.board.entity.BoardLinkImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardLinkImageRepository extends JpaRepository<BoardLinkImage, Long> {
    @Query("select bli from BoardLinkImage bli where bli.board.boardId = :boardId")
    List<BoardLinkImage> findByBoardId(@Param("boardId") Long boardId);
}
