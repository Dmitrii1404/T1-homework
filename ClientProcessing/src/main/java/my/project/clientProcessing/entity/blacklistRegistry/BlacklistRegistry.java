package my.project.clientProcessing.entity.blacklistRegistry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.lib.core.StatusEnum;
import my.project.clientProcessing.entity.client.DocumentType;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blacklist_registries",
    indexes = {
            @Index(name = "uk_blacklist_documentid", columnList = "document_id")
    })
public class BlacklistRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @Column(name = "document_id", nullable = false, unique = true)
    private String documentId;

    @Column(name = "black_listed_at", nullable = false)
    private LocalDate blacklistedAt;

    private String reason;

    @Column(name = "blacklist_expiration_date")
    private LocalDate blacklistExpirationDate;

    @PrePersist
    public void prePersist() {
        if (this.blacklistedAt == null) {
            this.blacklistedAt = LocalDate.now();
        }
        if (this.blacklistExpirationDate == null) {
            this.blacklistExpirationDate = LocalDate.now().plusYears(1);
        }
    }

}
