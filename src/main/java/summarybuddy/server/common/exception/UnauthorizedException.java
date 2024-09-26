package summarybuddy.server.common.exception;

import org.springframework.http.HttpStatus;
import summarybuddy.server.common.type.ErrorType;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(ErrorType errorType) {
        super(errorType, HttpStatus.UNAUTHORIZED);
    }
}
