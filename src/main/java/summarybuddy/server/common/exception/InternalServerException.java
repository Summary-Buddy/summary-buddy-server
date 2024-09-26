package summarybuddy.server.common.exception;

import org.springframework.http.HttpStatus;
import summarybuddy.server.common.type.ErrorType;

public class InternalServerException extends BaseException {
    public InternalServerException(ErrorType errorType) {
        super(errorType, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
