package summarybuddy.server.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MemberUpdateRequest(
        @NotNull @Schema(description = "회원 ID", example = "1") Long id,
        @Email(message = "Email should be valid")
                @Schema(description = "수정할 이메일", example = "summarybuddy@gmail.com")
                String newEmail,
        @Schema(description = "수정할 비밀번호", example = "p@ssword!!") String newPassword,
        @Schema(description = "수정할 비밀번호 확인", example = "p@ssword!!") String newPasswordConfirm) {}
