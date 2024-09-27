package summarybuddy.server.common.type.success;

import summarybuddy.server.common.type.SuccessType;

public enum MemberSuccessType implements SuccessType {
    JOIN_SUCCESS("회원가입에 성공하였습니다."),
    ALLOWED_USERNAME("사용 가능한 아이디 입니다."),
    UPDATE_SUCCESS("회원 정보 수정에 성공하였습니다."),
    SEARCH_USERNAME_SUCCESS("회원 USERNAME 검색에 성공하였습니다."),
    UPDATE_EMAIL_SUCCESS("회원 이메일 정보 수정에 성공하였습니다."),
    UPDATE_PASSWORD_SUCCESS("회원 비밀번호 정보 수정에 성공하였습니다."),
    GET_DETAIL_SUCCESS("회원 정보 조회에 성공하였습니다."),
    SEND_TEMPORARY_PASSWORD_SUCCESS("임시 비밀번호 전송에 성공하였습니다.");

    private final String message;

    MemberSuccessType(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
