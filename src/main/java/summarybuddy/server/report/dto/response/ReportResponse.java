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
                LocalDateTime createdAt) {
    public static ReportResponse of(Report report) {
        return new ReportResponse(
                report.getId(),
                report.getContent(),
                report.getFileDirectory(),
                report.getCreatedAt());
    }
}
