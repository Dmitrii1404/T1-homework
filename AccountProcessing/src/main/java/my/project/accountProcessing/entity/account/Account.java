package my.project.accountProcessing.entity.account;

import jakarta.persistence.*;
import lombok.*;
import my.lib.core.StatusEnum;

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

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal balance;

    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 4)
    private BigDecimal interestRate;

    @Column(name = "is_recalc", nullable = false)
    private Boolean isRecalc;

    @Column(name = "card_exist", nullable = false)
    private Boolean cardExist;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

}