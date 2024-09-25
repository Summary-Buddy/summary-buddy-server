package summarybuddy.server.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import summarybuddy.server.member.dto.request.MemberJoinRequest;
import summarybuddy.server.member.mapper.MemberMapper;
import summarybuddy.server.member.repository.MemberRepository;
import summarybuddy.server.member.repository.domain.Member;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public void save(MemberJoinRequest request) {
		if (memberRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new RuntimeException("Username already exists");
		} else if (!request.emailValidation()) {
			throw new RuntimeException("Email is not valid");
		} else if (!request.checkPassword()) {
			throw new RuntimeException("Passwords do not match");
		} else {
			Member member = MemberMapper.from(request, passwordEncoder.encode(request.getPassword()));
			memberRepository.save(member);
		}
	}
}
