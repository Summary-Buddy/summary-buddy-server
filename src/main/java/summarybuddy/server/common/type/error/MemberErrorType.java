package summarybuddy.server.common.type.error;

import summarybuddy.server.common.type.ErrorType;

public enum MemberErrorType implements ErrorType {
    USERNAME_ALREADY_EXIST("USERNAME 이 이미 존재합니다."),
    INVALID_EMAIL("EMAIL 형식이 유효하지 않습니다."),
    EMAIL_ALREADY_EXIST("EMAIL 이 이미 존재합니다."),
    PASSWORD_CANNOT_BE_EMPTY("PASSWORD 는 공백일 수 없습니다."),
    PASSWORD_NOT_MATCH("PASSWORD 와 확인 문자열이 일치하지 않습니다"),
    EMAIL_OR_PASSWORD_MUST_BE_PROVIDED("EMAIL 이나 PASSWORD 중 적어도 하나가 제공되어야 합니다."),
    NOT_FOUND("회원이 존재하지 않습니다."),
    USERNAME_AND_EMAIL_MUST_BE_PROVIDED("USERNAME 이랑 EMAIL 모두 제공되어야 합니다.");

    private final String message;

    MemberErrorType(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
