package ac.yongson.artgram.global.exception;

import ac.yongson.artgram.global.dto.ResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 인증 안됨
@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public ResponseDTO<?> body(){
        return new ResponseDTO<>(HttpStatus.UNAUTHORIZED, "unAuthorized", getMessage());
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}