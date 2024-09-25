package summarybuddy.server.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import summarybuddy.server.member.dto.request.MemberUpdateRequest;
import summarybuddy.server.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final MemberRepository memberRepository;

    public void validateUsername(String username) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
    }

    public void validateEmail(String email) {
        // 이메일 형식 검증 로직
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new RuntimeException("Invalid email format");
        }
        // 이메일 중복 체크 로직
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
    }

    public void validatePassword(String password, String confirmedPassword) {
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }
        if (!password.equals(confirmedPassword)) {
            throw new RuntimeException("Passwords do not match");
        }
    }

    public void validateUpdateRequest(MemberUpdateRequest request) {
        // 이메일 또는 비밀번호가 제공되어야 함
        if (request.getNewEmail() != null) {
            validateEmail(request.getNewEmail());
        }
        if (request.getNewPassword() != null) {
            validatePassword(request.getNewPassword(), request.getNewPasswordConfirm());
        }
        if (request.getNewEmail() == null && request.getNewPassword() == null) {
            throw new RuntimeException("At least one of email or password must be provided");
        }
    }
}
