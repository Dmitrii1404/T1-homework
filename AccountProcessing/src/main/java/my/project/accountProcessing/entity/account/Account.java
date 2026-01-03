package my.project.accountProcessing.entity.account;

import jakarta.persistence.*;
import lombok.*;
import my.lib.core.StatusEnum;
import my.project.accountProcessing.entity.payment.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Payment> payments;

    @PrePersist
    public void prePersist() {
        if (this.balance == null) {
            this.balance = BigDecimal.ZERO;
        }
        if (this.interestRate == null) {
            this.interestRate = BigDecimal.ZERO;
        }
        if (this.isRecalc == null) {
            this.isRecalc = true;
        }
        if (this.cardExist == null) {
            this.cardExist = false;
        }
    }

}