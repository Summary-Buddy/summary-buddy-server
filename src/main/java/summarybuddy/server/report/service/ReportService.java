package summarybuddy.server.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import summarybuddy.server.report.repository.ReportRepository;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final ReportRepository reportRepository;
}
