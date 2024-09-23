package summarybuddy.server.report.repository.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull
	private String content;

	@Column
	@NotNull
	private String fileDirectory;

	@Column
	@NotNull
	@CreatedDate
	private LocalDateTime createdAt;

	@Builder
	public Report(Long id, String content, String fileDirectory, LocalDateTime createdAt) {
		this.id = id;
		this.content = content;
		this.fileDirectory = fileDirectory;
		this.createdAt = createdAt;
	}
}
