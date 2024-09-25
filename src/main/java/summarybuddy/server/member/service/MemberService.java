package summarybuddy.server.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import summarybuddy.server.member.dto.request.MemberJoinRequest;
import summarybuddy.server.member.dto.request.MemberUpdateRequest;
import summarybuddy.server.member.dto.request.UsernameCheckRequest;
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

	public boolean usernameExists(UsernameCheckRequest request) {
		return memberRepository.findByUsername(request.getUsername()).isPresent();
	}

	public void updateMember(MemberUpdateRequest request) {
		Member member = memberRepository.findByEmail(request.getNewEmail())
				.orElseThrow(() -> new RuntimeException("Member not found"));

		// 비밀번호 변경
		if (request.getNewPassword() != null) {
			if (!request.checkPassword()) {
				throw new RuntimeException("Passwords do not match");
			}
			String encodedPassword = passwordEncoder.encode(request.getNewPassword());
			member.updatePassword(encodedPassword);
		}

		// 이메일 변경
		if (request.getNewEmail() != null) {
			if (!request.getNewEmail().contains("@")) {
				throw new RuntimeException("Email is not valid");
			}
			member.updateEmail(request.getNewEmail());
		}

		memberRepository.save(member);
	}
}
