package summarybuddy.server.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import summarybuddy.server.member.repository.MemberJpaRepository;
import summarybuddy.server.member.repository.domain.Member;
import org.springframework.security.core.userdetails.User;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberJpaRepository memberJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// username 으로 사용자를 찾음
		Member member = memberJpaRepository.findByUsername(username);

		// member 가 username 으로 검색에 실패하게 되면 예외 처리
		if (member == null) {
			throw new UsernameNotFoundException("Member not found with username: " + username);
		}

		// Member 의 Role 을 사용하여 User 객체 생성
		return User.builder()
				.username(member.getUsername())
				.password(member.getPassword())
				//.authorities(member.getRole().name()) // Enum Role 을 사용해 권한 부여
				.build();
	}
}
