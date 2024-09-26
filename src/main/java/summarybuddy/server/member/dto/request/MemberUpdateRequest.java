package summarybuddy.server.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequest {

    private String username;

    @Email(message = "Email should be valid")
    private String newEmail;

    private String newPassword;
    private String newPasswordConfirm;
}
