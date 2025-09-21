package my.project.accountProcessing.entity.account;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "accounts",
    indexes = {
            @Index(name = "idx_accounts_clientid", columnList = "client_id"),
            @Index(name = "idx_accounts_productid", columnList = "product_id")
    })
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "interest_rate", nullable = false)
    private BigDecimal interestRate = BigDecimal.ZERO;

    @Column(name = "is_recalc", nullable = false)
    private Boolean isRecalc = false;

    @Column(name = "card_exist", nullable = false)
    private Boolean cardExist = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

}