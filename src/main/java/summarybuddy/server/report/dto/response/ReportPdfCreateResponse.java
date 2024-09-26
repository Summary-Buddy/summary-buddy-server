package summarybuddy.server.report.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ReportPdfCreateResponse(
        @NotNull
                @Schema(
                        description = "PDF를 다운받을 수 있는 URL",
                        example = "https://storage.cloud.google.com/....pdf")
                String pdfDownloadUrl) {}
