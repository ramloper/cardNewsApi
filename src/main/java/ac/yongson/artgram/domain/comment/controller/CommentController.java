package ac.yongson.artgram.domain.comment.controller;

import ac.yongson.artgram.domain.board.dto.BoardRequestDTO;
import ac.yongson.artgram.domain.comment.dto.CommentRequestDTO;
import ac.yongson.artgram.domain.comment.service.CommentService;
import ac.yongson.artgram.global.dto.ResponseDTO;
import ac.yongson.artgram.global.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CommentController {
    private final AuthService authService;
    private final CommentService commentService;
    @PostMapping("auth/comment")
    public ResponseEntity<?> saveComment(@RequestBody CommentRequestDTO.SaveComment saveComment, HttpServletRequest request) {
        Long memberId = authService.getLoginMemberId(request);
        commentService.saveComment(memberId, saveComment);
        return ResponseEntity.ok().body(new ResponseDTO<>("댓글 등록 성공"));
    }
}
