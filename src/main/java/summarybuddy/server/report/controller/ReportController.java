package summarybuddy.server.report.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import summarybuddy.server.report.dto.request.ReportCreateRequest;
import summarybuddy.server.report.service.ReportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
	private final ReportService reportService;

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody ReportCreateRequest request) {
		return ResponseEntity.ok().build();
	}
}
