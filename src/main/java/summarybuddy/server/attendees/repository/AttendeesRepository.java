package summarybuddy.server.attendees.repository;

import java.util.List;
import summarybuddy.server.attendees.repository.domain.Attendees;

public interface AttendeesRepository {
    List<Attendees> findAllByMemberId(Long memberId);

    void saveAll(List<Attendees> attendees);
}
