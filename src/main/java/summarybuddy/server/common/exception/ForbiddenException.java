package summarybuddy.server.common.exception;

import org.springframework.http.HttpStatus;
import summarybuddy.server.common.type.ErrorType;

public class ForbiddenException extends BaseException {
    public ForbiddenException(ErrorType errorType) {
        super(errorType, HttpStatus.FORBIDDEN);
    }
}
