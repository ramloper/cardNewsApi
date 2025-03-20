package ac.yongson.artgram.domain.comment.service;

import ac.yongson.artgram.domain.comment.dto.CommentRequestDTO;
import ac.yongson.artgram.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;


    @Override
    public void saveComment(Long memberId, CommentRequestDTO.SaveComment saveComment) {
        commentRepository.save(saveComment.toEntity(memberId));
    }
}
