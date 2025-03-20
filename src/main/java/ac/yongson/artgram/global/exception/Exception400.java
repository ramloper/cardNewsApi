package ac.yongson.artgram.global.exception;

import ac.yongson.artgram.global.dto.ResponseDTO;
import ac.yongson.artgram.global.dto.ValidDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 유효성 검사 실패, 잘못된 파라메터 요청
@Getter
public class Exception400 extends RuntimeException {

    private final String key;
    private final String value;

    public Exception400(String key, String value) {
        super(value);
        this.key = key;
        this.value = value;
    }

    public ResponseDTO<?> body(){
        ValidDTO validDTO = new ValidDTO(key, value);
        return new ResponseDTO<>(HttpStatus.BAD_REQUEST, "badRequest", validDTO);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}