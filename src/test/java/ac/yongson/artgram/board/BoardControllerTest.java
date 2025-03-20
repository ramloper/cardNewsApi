package ac.yongson.artgram.board;

import ac.yongson.artgram.domain.board.controller.BoardController;
import ac.yongson.artgram.domain.board.dto.BoardRequestDTO;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class BoardControllerTest {
    @InjectMocks
    private BoardController target;

    private MockMvc mockMvc;
    private Gson gson;
    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .build();
    }
    @Test
    public void mockMvcIsNotNull(){
        mockMvc = MockMvcBuilders.standaloneSetup(target)
                .build();
        assertThat(target).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

//    @Test
//    public void accessTokenIsNull() throws Exception {
//        // given
//        final String url = "/api/auth/v1/board";
//        final List<MultipartFile> files = new ArrayList<>();
//        // when
//        final ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders.multipart(url)  // ✅ multipart 요청
//                        .param("title", "test")  // ✅ multipart 데이터 추가
//                        .param("content", "test")
//                        .contentType(MediaType.MULTIPART_FORM_DATA)
//        );
//
//        // then
//        resultActions.andExpect(status().is);
//    }

    private BoardRequestDTO.SaveBoard saveBoard() {
        return BoardRequestDTO.SaveBoard
                .builder()
                .title("test")
                .content("test")
                .build();
    }
}
