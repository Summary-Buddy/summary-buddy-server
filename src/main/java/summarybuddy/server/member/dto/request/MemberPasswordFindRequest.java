package summarybuddy.server.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MemberPasswordFindRequest(
        @NotNull
        @Schema(description = "회원의 Username", example = "summary_buddy123")
        String username,

        @NotNull
        @Email
        @Schema(description = "회원의 이메일", example = "summarybuddy@gmail.com")
        String email) {
}