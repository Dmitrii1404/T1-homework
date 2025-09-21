package my.project.clientProcessing.entity.client;

import jakarta.persistence.*;
import lombok.*;
import my.project.clientProcessing.entity.user.User;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clients",
    indexes = {
            @Index(name = "uk_client_clientid", columnList = "client_id")
    })
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false, unique = true)
    private String clientId;  // (формат clientId: XXFFNNNNNNNN, где XX - номер региона, FF - номер подразделения банка, N - порядковый. Например, 770100000001, 770200000023)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @Column(name = "document_id", nullable = false, unique = true)
    private String documentId;

    @Column(name = "document_prefix", nullable = false)
    private String documentPrefix;

    @Column(name = "document_suffix", nullable = false)
    private String documentSuffix;

}