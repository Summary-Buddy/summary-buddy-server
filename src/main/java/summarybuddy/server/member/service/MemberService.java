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
	private final ValidationService validationService;

	public void save(MemberJoinRequest request) {
		// 사용자 이름 검증
		validationService.validateUsername(request.getUsername());

		// 이메일 검증
		validationService.validateEmail(request.getEmail());

		// 비밀번호 검증
		validationService.validatePassword(request.getPassword(), request.getPasswordConfirm());

		Member member = MemberMapper.from(request, passwordEncoder.encode(request.getPassword()));
		memberRepository.save(member);
	}

	public void updateMember(String username, MemberUpdateRequest request) {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Member not found"));

		// 비밀번호 변경
		if (request.getNewPassword() != null) {
			validationService.validatePassword(request.getNewPassword(), request.getNewPasswordConfirm());
			String encodedPassword = passwordEncoder.encode(request.getNewPassword());
			member.updatePassword(encodedPassword);
		}

		// 이메일 변경
		if (request.getNewEmail() != null && !member.getEmail().equals(request.getNewEmail())) {
			validationService.validateEmail(request.getNewEmail());
			member.updateEmail(request.getNewEmail());
		}

		memberRepository.save(member);
	}
}
