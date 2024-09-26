package summarybuddy.server.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UsernameCheckRequest(
        @NotNull @Schema(description = "로그인 시 사용될 ID", example = "summary_buddy123")
                String username) {}
