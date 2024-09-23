package summarybuddy.server.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import summarybuddy.server.report.repository.domain.Report;

public interface ReportJpaRepository extends JpaRepository<Report, Long> {}
