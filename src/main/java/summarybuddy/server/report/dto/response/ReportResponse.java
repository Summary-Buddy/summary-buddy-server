package summarybuddy.server.report.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import summarybuddy.server.report.repository.domain.Report;

public record ReportResponse(
        @NotNull @Schema(description = "회의록 ID", example = "1") Long id,
        @NotNull @Schema(description = "요약된 회의록 내용", example = "## 프로젝트 관련 회의") String content,
        @NotNull @Schema(description = "파일 경로", example = "https://bucket-name/pdf/example.pdf")
                String fileDirectory,
        @NotNull @Schema(description = "회의록 저장 시간", example = "2024-10-10 10:00:00")
                LocalDateTime createdAt,
        @NotNull @Schema(description = "회의록 PDF 미리보기 글자", example = "1010 회의록")
                String previewTitle) {
    public static ReportResponse of(Report report, String previewTitle) {
        return new ReportResponse(
                report.getId(),
                report.getContent(),
                report.getFileDirectory(),
                report.getCreatedAt(),
                previewTitle);
    }
}
