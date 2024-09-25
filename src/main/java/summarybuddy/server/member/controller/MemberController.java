package summarybuddy.server.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import summarybuddy.server.member.dto.request.MemberJoinRequest;
import summarybuddy.server.member.dto.request.MemberUpdateRequest;
import summarybuddy.server.member.dto.request.UsernameCheckRequest;
import summarybuddy.server.member.service.MemberService;
import summarybuddy.server.member.service.ValidationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
	private final MemberService memberService;
	private final ValidationService validationService;

	@PostMapping("/join")
	public ResponseEntity<?> join(@Valid @RequestBody MemberJoinRequest request) {
		memberService.save(request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/check-username")
	public ResponseEntity<Boolean> checkUsername(@Valid @RequestBody UsernameCheckRequest request) {
		try {
			validationService.validateUsername(request.getUsername());
			return ResponseEntity.ok(false); // 존재 X = false
		} catch (RuntimeException e) {
			return ResponseEntity.ok(true); // 존재 = ture
		}
	}

	@PatchMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody MemberUpdateRequest request) {
		validationService.validateUpdateRequest(request);
		memberService.updateMember(request.getMemberId(), request);
		return ResponseEntity.ok().build();
	}
}
