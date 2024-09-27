package summarybuddy.server.report.repository;

import java.util.List;
import java.util.Optional;
import summarybuddy.server.report.repository.domain.Report;

public interface ReportRepository {
    Report save(Report report);

    Optional<Report> findById(Long reportId);

    void deleteAllById(List<Long> reportIds);
}
