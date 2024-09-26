package summarybuddy.server.report.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ReportPdfCreateRequest(
        @NotNull @Schema(description = "회의록 ID", example = "1") Long reportId) {}
