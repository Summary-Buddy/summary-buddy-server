package summarybuddy.server.member.repository.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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
}
