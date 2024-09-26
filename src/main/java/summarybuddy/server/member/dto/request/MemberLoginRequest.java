package summarybuddy.server.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MemberLoginRequest(
        @NotBlank(message = "Username is required")
                @Schema(description = "로그인 시 입력된 ID", example = "summary_buddy123")
                String username,
        @NotBlank(message = "Password is required")
                @Schema(description = "비밀번호", example = "p@ssword")
                String password) {}
