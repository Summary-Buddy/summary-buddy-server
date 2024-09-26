package summarybuddy.server.common.type.error;

import summarybuddy.server.common.type.ErrorType;

public enum CommonErrorType implements ErrorType {
    REQUEST_VALIDATION("입력 값 검증에 실패하였습니다"),
    INVALID_TYPE("잘못된 타입이 입력되었습니다"),
    INVALID_MISSING_HEADER("요청에 필요한 헤더값이 존재하지 않습니다"),
    INVALID_HTTP_REQUEST("허용되지 않는 문자열이 입력되었습니다"),
    METHOD_NOT_ALLOWED("잘못된 HTTP 메소드의 요청입니다"),
    INTERNAL_SERVER("알 수 없는 서버 에러가 발생했습니다"),
    VALIDATION_FAILED("정상적이지 않은 입력값입니다"),
    BAD_REQUEST("잘못된 요청입니다"),
    MISSING_PARAM("요청 파라미터가 누락되었습니다"),
    RUNTIME("런타임 예외가 발생했습니다"),
    IO("I/O 예외가 발생했습니다"),
    NULL("객체가 null 인 상태에서 접근하였습니다"),
    NO_SUCH_ELEMENT("요소가 존재하지 않습니다");

    private final String message;

    CommonErrorType(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
