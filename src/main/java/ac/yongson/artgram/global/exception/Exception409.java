package ac.yongson.artgram.global.exception;

import ac.yongson.artgram.global.dto.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 권한 없음
@Getter
public class Exception409 extends RuntimeException {
    public Exception409(String message) {
        super(message);
    }

    public ResponseDTO<?> body(){
        return new ResponseDTO<>(HttpStatus.CONFLICT, "OpenAI Unknown Error", getMessage());
    }

    public HttpStatus status(){
        return HttpStatus.NOT_FOUND;
    }
}