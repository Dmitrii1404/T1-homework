package my.project.accountProcessing.entity.payment;

import jakarta.persistence.*;
import lombok.*;
import my.project.accountProcessing.entity.account.Account;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "is_credit", nullable = false)
    private Boolean isCredit;

    @Column(name = "payed_at")
    private LocalDate payedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

}