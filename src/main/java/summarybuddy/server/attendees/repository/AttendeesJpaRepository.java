package summarybuddy.server.attendees.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import summarybuddy.server.attendees.repository.domain.Attendees;

public interface AttendeesJpaRepository extends JpaRepository<Attendees, Long> {}
