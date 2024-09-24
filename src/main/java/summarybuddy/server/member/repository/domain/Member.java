package summarybuddy.server.member.repository.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String username;

	@Email
	@Column(unique = true)
	private String email;

	@NotNull
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;
}