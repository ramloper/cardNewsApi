package ac.yongson.artgram.domain.board.service;

import ac.yongson.artgram.domain.board.dto.BoardRequestDTO;
import ac.yongson.artgram.domain.board.dto.BoardResponseDTO;
import ac.yongson.artgram.domain.board.entity.Board;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    void saveBoard(BoardRequestDTO.SaveBoard saveBoard, List<MultipartFile> files, Long memberId);
    List<BoardResponseDTO.Post> getBoards(Long memberId);
    List<BoardResponseDTO.Post> getMyBoards(Long memberId);
    List<BoardResponseDTO.Post> getBoardLikeList(Long memberId);
    BoardResponseDTO.BoardInfo getBoard(Long memberId,Long boardId);
    void saveBoardLike(Long memberId, Long boardId);
    void deleteBoardLike(Long memberId, Long boardId);
}
