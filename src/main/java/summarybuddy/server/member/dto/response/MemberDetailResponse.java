package summarybuddy.server.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MemberDetailResponse(
        @NotNull @Schema(description = "회원 ID", example = "1") Long id,
        @NotNull @Schema(description = "회원 닉네임", example = "summary_buddy123") String username,
        @NotNull @Schema(description = "회원 이메일", example = "summarybuddy@gmail.com")
                String email) {}
