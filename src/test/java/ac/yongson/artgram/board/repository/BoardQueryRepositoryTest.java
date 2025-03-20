package ac.yongson.artgram.board.repository;

import ac.yongson.artgram.domain.board.dto.BoardResponseDTO;
import ac.yongson.artgram.domain.board.entity.Board;
import ac.yongson.artgram.domain.board.repository.BoardQueryRepository;
import ac.yongson.artgram.domain.member.entity.Member;
import ac.yongson.artgram.domain.member.repository.MemberRepository;
import ac.yongson.artgram.domain.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardQueryRepositoryTest {
    @Autowired
    private BoardQueryRepository boardQueryRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void getMainPostList() {
        // given
        Member member = memberRepository.save(Member.builder()
                .memberName("테스트")
                .memberRole(Member.MemberRole.USER)
                .build());

        Board board = boardRepository.save(Board.builder()
                .title("테스트")
                .content("테스트")
                .memberId(member.getMemberId())
                .build());

        // when
        List<BoardResponseDTO.Post> result = boardQueryRepository.getMainPostList();

        // then
        assertThat(result).isNotEmpty();
        BoardResponseDTO.Post post = result.get(0);
        assertThat(post.getMemberName()).isEqualTo("테스트");
    }
}