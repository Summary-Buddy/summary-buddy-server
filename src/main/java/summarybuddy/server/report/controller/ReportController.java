package summarybuddy.server.report.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import summarybuddy.server.common.annotation.LoginMember;
import summarybuddy.server.report.dto.request.ReportCreateRequest;
import summarybuddy.server.report.service.ReportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<?> create(
            @LoginMember String username,
            @RequestPart("file") MultipartFile file,
            @RequestPart("content") ReportCreateRequest request) {
        reportService.save(username, file, request);
        return ResponseEntity.ok().build();
    }
}
