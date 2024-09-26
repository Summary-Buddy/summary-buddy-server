package summarybuddy.server.common.exception;

import org.springframework.http.HttpStatus;
import summarybuddy.server.common.type.ErrorType;

public class BadRequestException extends BaseException {
    public BadRequestException(ErrorType errorType) {
        super(errorType, HttpStatus.BAD_REQUEST);
    }
}
