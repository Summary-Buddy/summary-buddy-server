package summarybuddy.server.report.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ReportCreateRequest(
        @Schema(description = "작성자를 제외한 참석자 ID 목록", example = "[1, 2, 3]")
                List<Long> memberIdList) {}
