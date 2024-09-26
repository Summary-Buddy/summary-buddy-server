package summarybuddy.server.common.exception;

import org.springframework.http.HttpStatus;
import summarybuddy.server.common.type.ErrorType;

public class UsernameAlreadyExistsException extends BaseException {
    public UsernameAlreadyExistsException(ErrorType errorType) {
        super(errorType, HttpStatus.BAD_REQUEST);
    }
}
