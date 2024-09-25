package summarybuddy.server.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import summarybuddy.server.member.dto.request.MemberJoinRequest;
import summarybuddy.server.member.dto.request.UsernameCheckRequest;
import summarybuddy.server.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/join")
	public ResponseEntity<?> join(@Valid @RequestBody MemberJoinRequest request) {
		memberService.save(request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/check-username")
	public ResponseEntity<Boolean> checkUsername(@Valid @RequestBody UsernameCheckRequest request) {
		boolean isExists = memberService.usernameExists(request.getUsername());
		return ResponseEntity.ok(isExists);
	}
}
