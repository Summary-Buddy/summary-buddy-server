package summarybuddy.server.common.type.success;

import summarybuddy.server.common.type.SuccessType;

public enum ReportSuccessType implements SuccessType {
    CREATE_SUCCESS("회의록 생성에 성공하였습니다."),
    GET_LIST_SUCCESS("회의록 목록 조회에 성공하였습니다."),
    GET_SUCCESS("회의록 단건 조회에 성공하였습니다."),
    CREATE_PDF_SUCCESS("PDF 파일 생성에 성공하였습니다.");

    private final String message;

    ReportSuccessType(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
