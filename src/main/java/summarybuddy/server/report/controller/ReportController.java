package summarybuddy.server.report.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import summarybuddy.server.attendees.service.AttendeesService;
import summarybuddy.server.common.annotation.LoginMember;
import summarybuddy.server.report.dto.request.ReportCreateRequest;
import summarybuddy.server.report.dto.response.ReportResponse;
import summarybuddy.server.report.dto.response.SimpleReportResponse;
import summarybuddy.server.report.repository.domain.Report;
import summarybuddy.server.report.service.ReportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;
    private final AttendeesService attendeesService;

    @PostMapping
    public ResponseEntity<?> create(
            @LoginMember Long memberId,
            @RequestPart("file") MultipartFile file,
            @RequestPart("content") ReportCreateRequest request) {
        Report report = reportService.save(memberId, file, request);
        attendeesService.save(memberId, report, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<SimpleReportResponse>> readAll(@LoginMember Long memberId) {
        List<SimpleReportResponse> response = attendeesService.findReportsByMemberId(memberId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResponse> read(@PathVariable("reportId") Long reportId) {
        ReportResponse response = reportService.findById(reportId);
        return ResponseEntity.ok().body(response);
    }
}
