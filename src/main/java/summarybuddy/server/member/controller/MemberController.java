package summarybuddy.server.member.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import summarybuddy.server.common.dto.ApiResponse;
import summarybuddy.server.common.type.success.MemberSuccessType;
import summarybuddy.server.member.dto.request.MemberJoinRequest;
import summarybuddy.server.member.dto.request.MemberUpdateRequest;
import summarybuddy.server.member.dto.request.UsernameCheckRequest;
import summarybuddy.server.member.dto.response.SimpleMemberResponse;
import summarybuddy.server.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ApiResponse<?> join(@Valid @RequestBody MemberJoinRequest request) {
        memberService.save(request);
        return ApiResponse.success(MemberSuccessType.JOIN_SUCCESS);
    }

    @PostMapping("/check-username")
    public ApiResponse<?> checkUsername(@Valid @RequestBody UsernameCheckRequest request) {
        memberService.checkUsername(request.username());
        return ApiResponse.success(MemberSuccessType.ALLOWED_USERNAME);
    }

    @PatchMapping("/update")
    public ApiResponse<?> update(@Valid @RequestBody MemberUpdateRequest request) {
        memberService.updateMember(request.id(), request);
        return ApiResponse.success(MemberSuccessType.UPDATE_SUCCESS);
    }

    @GetMapping("/search")
    public ApiResponse<List<SimpleMemberResponse>> search(@RequestParam("query") String query) {
        List<SimpleMemberResponse> response = memberService.findByUsernameLike(query);
        return ApiResponse.success(MemberSuccessType.SEARCH_USERNAME_SUCCESS, response);
    }
}
