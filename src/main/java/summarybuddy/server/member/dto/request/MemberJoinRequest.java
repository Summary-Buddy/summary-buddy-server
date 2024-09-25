package summarybuddy.server.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinRequest {
	@NotBlank(message = "Username is required")
	private String username;

	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "Password is required")
	//@Size(min = 8, message = "Password must be at least 8 characters")
	private String password;

	@NotBlank(message = "Password confirmation is required")
	private String passwordConfirm;

	//private String role;

	@Builder
	public MemberJoinRequest(String username, String email, String password, String passwordConfirm) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}
}
