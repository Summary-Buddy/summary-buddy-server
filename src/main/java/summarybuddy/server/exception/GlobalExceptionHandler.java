package summarybuddy.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import summarybuddy.server.member.dto.response.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ResponseDto> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDto> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto(ex.getMessage()));
    }
}