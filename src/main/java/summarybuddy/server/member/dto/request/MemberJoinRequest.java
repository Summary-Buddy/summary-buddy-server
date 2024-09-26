package summarybuddy.server.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record MemberJoinRequest(
        @NotBlank(message = "Username is required")
                @Schema(description = "로그인 시 사용할 ID", example = "summary_buddy123")
                String username,
        @NotBlank
                @Email(message = "Email should be valid")
                @Schema(description = "이메일", example = "summarybuddy@gmail.com")
                String email,
        @NotBlank(message = "Password is required")
                // @Size(min = 8, message = "Password must be at least 8 characters")
                @Schema(description = "비밀번호", example = "p@ssword")
                String password,
        @NotBlank(message = "Password confirmation is required")
                @Schema(description = "비밀번호 확인", example = "p@ssword")
                String passwordConfirm) {
    @Builder
    public MemberJoinRequest(
            String username, String email, String password, String passwordConfirm) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
