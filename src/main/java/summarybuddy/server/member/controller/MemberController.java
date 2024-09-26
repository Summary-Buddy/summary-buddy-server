package summarybuddy.server.member.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import summarybuddy.server.member.dto.request.MemberJoinRequest;
import summarybuddy.server.member.dto.response.SimpleMemberResponse;
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

    @GetMapping("/search")
    public ResponseEntity<List<SimpleMemberResponse>> search(@RequestParam("query") String query) {
        List<SimpleMemberResponse> response = memberService.findByUsernameLike(query);
        return ResponseEntity.ok().body(response);
    }
}
