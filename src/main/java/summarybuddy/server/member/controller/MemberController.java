package summarybuddy.server.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import summarybuddy.server.member.dto.request.MemberJoinRequest;
import summarybuddy.server.member.dto.request.MemberUpdateRequest;
import summarybuddy.server.member.dto.request.UsernameCheckRequest;
import summarybuddy.server.member.dto.response.ResponseDto;
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
		validationService.validateUsername(request.getUsername());
		return ResponseEntity.ok(false); // 존재 X = false
	}

	@PatchMapping("/update")
	public ResponseEntity<ResponseDto> update(@Valid @RequestBody MemberUpdateRequest request) {
		validationService.validateUpdateRequest(request);
		memberService.updateMember(request.getUsername(), request);
		return ResponseEntity.ok(new ResponseDto(true, "회원 정보가 성공적으로 수정되었습니다."));
	}
}
