package ac.yongson.artgram.domain.board.controller;

import ac.yongson.artgram.domain.board.dto.BoardRequestDTO;
import ac.yongson.artgram.domain.board.service.BoardService;
import ac.yongson.artgram.global.dto.ResponseDTO;
import ac.yongson.artgram.global.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class BoardController {
    private final AuthService authService;
    private final BoardService boardService;

    @PostMapping("auth/board")
    public ResponseEntity<?> saveBoard(@ModelAttribute BoardRequestDTO.SaveBoard saveBoard,
                                       @RequestPart(value = "files", required = false) List<MultipartFile> files, HttpServletRequest request) {
        Long memberId = authService.getLoginMemberId(request);
        boardService.saveBoard(saveBoard,files,memberId);
        return ResponseEntity.ok().body(new ResponseDTO<>("게시판 등록 성공"));
    }

    @GetMapping("auth/board/list")
    public ResponseEntity<?> getBoardList(HttpServletRequest request) {
        Long memberId = authService.getLoginMemberId(request);
        return ResponseEntity.ok().body(new ResponseDTO<>(boardService.getBoards(memberId)));
    }

    @GetMapping("auth/board/my/list")
    public ResponseEntity<?> getMyBoardList(HttpServletRequest request) {
        Long memberId = authService.getLoginMemberId(request);
        return ResponseEntity.ok().body(new ResponseDTO<>(boardService.getMyBoards(memberId)));
    }

    @GetMapping("auth/board/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable("boardId")Long boardId, HttpServletRequest request) {
        Long memberId = authService.getLoginMemberId(request);
        return ResponseEntity.ok().body(new ResponseDTO<>(boardService.getBoard(memberId, boardId)));
    }

    @PostMapping("auth/board/like/{boardId}")
    public ResponseEntity<?> postBoardLike(@PathVariable("boardId")Long boardId, HttpServletRequest request) {
        Long memberId = authService.getLoginMemberId(request);
        boardService.saveBoardLike(memberId,boardId);
        return ResponseEntity.ok().body(new ResponseDTO<>("좋아요 성공"));
    }
    @DeleteMapping("auth/board/like/{boardId}")
    public ResponseEntity<?> deleteBoardLike(@PathVariable("boardId")Long boardId, HttpServletRequest request) {
        Long memberId = authService.getLoginMemberId(request);
        boardService.deleteBoardLike(memberId,boardId);
        return ResponseEntity.ok().body(new ResponseDTO<>("좋아요 취소 성공"));
    }

}
