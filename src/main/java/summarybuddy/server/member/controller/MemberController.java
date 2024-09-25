package summarybuddy.server.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import summarybuddy.server.config.JwtUtil;
import summarybuddy.server.member.dto.request.JoinDto;
import summarybuddy.server.member.dto.request.LoginDto;
import summarybuddy.server.member.repository.MemberJpaRepository;
import summarybuddy.server.member.repository.domain.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
	private final MemberJpaRepository memberJpaRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@PostMapping({"/join"})
	public ResponseEntity<String> join(@Valid @RequestBody JoinDto joinDto) {
		if (this.memberJpaRepository.findByUsername(joinDto.getUsername()) != null) {
			return ResponseEntity.badRequest().body("Username already exists");
		} else if (!joinDto.isValidEmail()) {
			return ResponseEntity.badRequest().body("Email is not valid");
		} else if (!joinDto.checkPassword()) {
			return ResponseEntity.badRequest().body("Passwords do not match");
		} else {
			Member member = new Member();
			member.setEmail(joinDto.getEmail());
			member.setUsername(joinDto.getUsername());
			member.setPassword(this.passwordEncoder.encode(joinDto.getPassword()));
			this.memberJpaRepository.save(member);
			return ResponseEntity.ok("User created successfully");
		}
	}

	@PostMapping({"/login"})
	public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
		Authentication authentication =
				this.authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								loginDto.getUsername(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return ResponseEntity.ok(this.jwtUtil.generateToken(userDetails));
	}
}
