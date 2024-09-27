package summarybuddy.server.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import summarybuddy.server.attendees.service.AttendeesService;
import summarybuddy.server.common.annotation.LoginMember;
import summarybuddy.server.common.dto.ApiResponse;
import summarybuddy.server.common.type.success.ReportSuccessType;
import summarybuddy.server.report.dto.request.ReportCreateRequest;
import summarybuddy.server.report.dto.request.ReportPdfCreateRequest;
import summarybuddy.server.report.dto.response.ReportPdfCreateResponse;
import summarybuddy.server.report.dto.response.ReportResponse;
import summarybuddy.server.report.dto.response.SimpleReportResponse;
import summarybuddy.server.report.repository.domain.Report;
import summarybuddy.server.report.service.ReportService;

@RestController
@Tag(name = "회의록")
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;
    private final AttendeesService attendeesService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "회의 녹음을 토대로 요약된 회의록 생성")
    public ApiResponse<?> create(
            @LoginMember Long memberId,
            @RequestPart("file") MultipartFile file,
            @RequestPart("content") ReportCreateRequest request) {
        Report report = reportService.save(memberId, file, request);
        attendeesService.save(memberId, report, request);
        return ApiResponse.success(ReportSuccessType.CREATE_SUCCESS);
    }

    @GetMapping
    @Operation(summary = "회원이 참여한 회의록 목록 조회")
    public ApiResponse<List<SimpleReportResponse>> readAll(@LoginMember Long memberId) {
        List<SimpleReportResponse> response = attendeesService.findReportsByMemberId(memberId);
        return ApiResponse.success(ReportSuccessType.GET_LIST_SUCCESS, response);
    }

    @GetMapping("/{reportId}")
    @Operation(summary = "회의록 단건 조회")
    public ApiResponse<ReportResponse> read(@PathVariable("reportId") Long reportId) {
        ReportResponse response = reportService.findById(reportId);
        return ApiResponse.success(ReportSuccessType.GET_SUCCESS, response);
    }

    @PostMapping("/pdf")
    @Operation(summary = "회의록 PDF 다운로드 링크 조회")
    public ApiResponse<ReportPdfCreateResponse> createPdf(
            @Valid @RequestBody ReportPdfCreateRequest request) {
        ReportPdfCreateResponse pdfUrl = reportService.createPdfUrl(request);
        return ApiResponse.success(ReportSuccessType.CREATE_PDF_SUCCESS, pdfUrl);
    }
}
