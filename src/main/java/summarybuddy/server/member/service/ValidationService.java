package summarybuddy.server.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}
