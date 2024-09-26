package summarybuddy.server.common.type.success;

import summarybuddy.server.common.type.SuccessType;

public enum MemberSuccessType implements SuccessType {
    JOIN_SUCCESS("회원가입에 성공하였습니다."),
    ALLOWED_USERNAME("사용 가능한 아이디 입니다."),
    UPDATE_SUCCESS("회원 정보 수정에 성공하였습니다.");

    private final String message;

    MemberSuccessType(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
