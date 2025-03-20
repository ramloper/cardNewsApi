package ac.yongson.artgram.board;

import ac.yongson.artgram.domain.board.dto.BoardRequestDTO;
import ac.yongson.artgram.domain.board.entity.Board;
import ac.yongson.artgram.domain.board.repository.BoardRepository;
import ac.yongson.artgram.domain.board.service.impl.BoardServiceImpl;
import ac.yongson.artgram.domain.comment.entity.Comment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {
    @InjectMocks
    private BoardServiceImpl boardService;
    @Mock
    private BoardRepository boardRepository;

    private final Long memberId = 1L;
    @Test
    public void saveBoardTest() {
        //given
        BoardRequestDTO.SaveBoard saveDTO = BoardRequestDTO.SaveBoard
                .builder()
                .title("test")
                .content("test")
                .build();

        doReturn(board()).when(boardRepository).save(any(Board.class));

        //when
//        boardService.saveBoard(saveDTO, memberId);

        // verify
//        verify(boardRepository, times(1)).save(any(Board.class));
    }

    private Board board() {
        List<Comment> comments = new ArrayList<>();
        return Board.builder()
                .boardId(1L)
                .title("test")
                .content("test")
                .memberId(1L)
                .imageIds(List.of(1L,2L))
                .comments(comments)
                .build();
    }
}
