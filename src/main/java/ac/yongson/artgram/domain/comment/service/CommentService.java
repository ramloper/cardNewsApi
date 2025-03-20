package ac.yongson.artgram.domain.comment.service;

import ac.yongson.artgram.domain.comment.dto.CommentRequestDTO;

public interface CommentService {
    void saveComment(Long memberId, CommentRequestDTO.SaveComment saveComment);
}
