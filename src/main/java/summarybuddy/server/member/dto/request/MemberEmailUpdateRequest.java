package summarybuddy.server.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MemberEmailUpdateRequest(
        @NotNull
        @Schema(description = "수정할 회원 ID", example = "1")
        Long id,
        @Email
        @Schema(description = "수정할 이메일", example = "summary_buddy989@naver.com")
        String email) {
}
