package summarybuddy.server.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import summarybuddy.server.common.dto.ApiResponse;
import summarybuddy.server.common.type.success.MemberSuccessType;
import summarybuddy.server.member.dto.request.*;
import summarybuddy.server.member.dto.response.MemberDetailResponse;
import summarybuddy.server.member.dto.response.SimpleMemberResponse;
import summarybuddy.server.member.service.MemberService;

@RestController
@Tag(name = "회원")
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    @Operation(summary = "회원 가입")
    public ApiResponse<?> join(@Valid @RequestBody MemberJoinRequest request) {
        memberService.save(request);
        return ApiResponse.success(MemberSuccessType.JOIN_SUCCESS);
    }

    @PostMapping("/check-username")
    @Operation(summary = "USERNAME 중복 체크")
    public ApiResponse<?> checkUsername(@Valid @RequestBody UsernameCheckRequest request) {
        memberService.checkUsername(request.username());
        return ApiResponse.success(MemberSuccessType.ALLOWED_USERNAME);
    }

    @PatchMapping("/update")
    @Operation(summary = "회원 정보 수정")
    public ApiResponse<?> update(@Valid @RequestBody MemberUpdateRequest request) {
        memberService.updateMember(request.id(), request);
        return ApiResponse.success(MemberSuccessType.UPDATE_SUCCESS);
    }

    @GetMapping("/search")
    @Operation(summary = "회원 USERNAME 검색")
    public ApiResponse<List<SimpleMemberResponse>> search(@RequestParam("query") String query) {
        List<SimpleMemberResponse> response = memberService.findByUsernameLike(query);
        return ApiResponse.success(MemberSuccessType.SEARCH_USERNAME_SUCCESS, response);
    }

    @PatchMapping("/email-update")
    @Operation(summary = "회원 이메일 수정")
    public ApiResponse<?> updateEmail(@Valid @RequestBody MemberEmailUpdateRequest request) {
        memberService.updateEmail(request);
        return ApiResponse.success(MemberSuccessType.UPDATE_EMAIL_SUCCESS);
    }

    @PatchMapping("/password-update")
    @Operation(summary = "회원 비밀번호 수정")
    public ApiResponse<?> updateEmail(@Valid @RequestBody MemberPasswordUpdateRequest request) {
        memberService.updatePassword(request);
        return ApiResponse.success(MemberSuccessType.UPDATE_PASSWORD_SUCCESS);
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "회원 정보 조회")
    public ApiResponse<MemberDetailResponse> get(@PathVariable("memberId") Long id) {
        MemberDetailResponse response = memberService.findById(id);
        return ApiResponse.success(MemberSuccessType.GET_DETAIL_SUCCESS, response);
    }

    @PostMapping("/password-reset")
    @Operation(summary = "비밀번호 찾기")
    public ApiResponse<?> resetPassword(@Valid @RequestBody MemberPasswordFindRequest request) {
        memberService.resetPassword(request);
        return ApiResponse.success(MemberSuccessType.SEND_TEMPORARY_PASSWORD_SUCCESS);
    }
}
