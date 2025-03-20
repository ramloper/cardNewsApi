package ac.yongson.artgram.board;

import ac.yongson.artgram.domain.board.entity.Board;
import ac.yongson.artgram.domain.board.repository.BoardRepository;
import ac.yongson.artgram.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
// DataJpaTest는 기본이 H2 Db 사용 , 아래 어노테이션 사용시 실제 DB로 연결되기 때문에 테스트 성공 가능
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void BoardRepositoryIsNotNull() {
        assertThat(boardRepository).isNotNull();
    }

    @Test
    public void saveBoard() {
        // given
        final List<Long> imageIds = List.of(1L, 2L);
        final Board board = Board
                .builder()
                .title("테스트")
                .content("테스트")
                .memberId(1L) // 미리 저장된 Member의 ID
                .build();
        board.addImagesId(imageIds);

        // When
        Board savedBoard = boardRepository.save(board);

        // Then
        assertThat(savedBoard).isNotNull();
        assertThat(savedBoard.getBoardId()).isNotNull();
        assertThat(savedBoard.getTitle()).isEqualTo("테스트");
        assertThat(savedBoard.getContent()).isEqualTo("테스트");
        assertThat(savedBoard.getImageIds()).hasSize(2);
        assertThat(savedBoard.getComments()).hasSize(0);
    }
}
