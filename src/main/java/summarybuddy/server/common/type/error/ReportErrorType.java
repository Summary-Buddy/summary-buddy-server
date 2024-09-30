package summarybuddy.server.common.type.error;

import summarybuddy.server.common.type.ErrorType;

public enum ReportErrorType implements ErrorType {
    NOT_FOUND("회의록이 존재하지 않습니다."),
    NO_SUMMARY_RESULT("회의록 요약 결과가 존재하지 않습니다."),
    WAV_CONVERT("WAV 파일 변환 과정 중 문제가 발생하였습니다."),
    MP3_CONVERT("MP3 파일 변환 과정 중 문제가 발생하였습니다.");

    private final String message;

    ReportErrorType(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
