package summarybuddy.server.report.repository;

import summarybuddy.server.report.repository.domain.Report;

public interface ReportRepository {
    Report save(Report report);
}
