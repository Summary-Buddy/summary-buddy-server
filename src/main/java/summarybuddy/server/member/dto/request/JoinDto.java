package summarybuddy.server.member.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class JoinDto {
	private String username;
	private String password;
	private String passwordConfirm;
	private String email;
	private String role;

	// 패스워드 확인 메서드
	public boolean checkPassword() {
		return this.password != null && this.password.equals(this.passwordConfirm);
	}

	// 이메일 형식 확인 메서드
	public boolean isValidEmail() {
		return this.email != null && this.email.contains("@");
	}
}
