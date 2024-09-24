package summarybuddy.server.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class JoinDto {
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

	// 패스워드 확인 메서드
	public boolean checkPassword() {
		return this.password != null && this.password.equals(this.passwordConfirm);
	}

	// 이메일 형식 확인 메서드
	public boolean isValidEmail() {
		return this.email != null && this.email.contains("@");
	}
}
