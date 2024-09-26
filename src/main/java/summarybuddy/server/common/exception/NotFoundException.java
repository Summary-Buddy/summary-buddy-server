package summarybuddy.server.common.exception;

import org.springframework.http.HttpStatus;
import summarybuddy.server.common.type.ErrorType;

public class NotFoundException extends BaseException {
    public NotFoundException(ErrorType errorType) {
        super(errorType, HttpStatus.NOT_FOUND);
    }
}
