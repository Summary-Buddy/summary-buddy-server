package summarybuddy.server.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {
    @Email(message = "Email should be valid")
    private String newEmail;

    private String newPassword;
    private String newPasswordConfirm;

    public boolean isPasswordConfirmed() {
        return this.newPassword != null && this.newPassword.equals(this.newPasswordConfirm);
    }

    public boolean isEmailValid() {
        return this.newEmail != null && this.newEmail.contains("@");
    }
}
