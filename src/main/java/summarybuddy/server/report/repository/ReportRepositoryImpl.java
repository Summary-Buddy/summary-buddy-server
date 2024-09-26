package summarybuddy.server.report.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import summarybuddy.server.report.repository.domain.Report;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {
    private final ReportJpaRepository reportJpaRepository;

    @Override
    public void save(Report report) {
        reportJpaRepository.save(report);
    }
}
