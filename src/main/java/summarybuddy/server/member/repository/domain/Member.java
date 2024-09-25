package summarybuddy.server.member.repository.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String username;

	@Email
	@NotNull
	@Column(unique = true)
	private String email;

	@NotNull
	private String password;

	// Enum으로 설정
	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public Member(Long id, String username, String email, String password, Role role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}
}
