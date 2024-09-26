package summarybuddy.server.report.repository.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String content;

    @Column @NotNull private String fileDirectory;

    @Column @NotNull @CreatedDate private LocalDateTime createdAt;

    @Builder
    public Report(Long id, String content, String fileDirectory, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.fileDirectory = fileDirectory;
        this.createdAt = createdAt;
    }
}
