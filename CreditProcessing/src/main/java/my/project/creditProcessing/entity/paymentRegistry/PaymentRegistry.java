package my.project.creditProcessing.entity.paymentRegistry;

import jakarta.persistence.*;
import lombok.*;
import my.project.creditProcessing.entity.productRegistry.ProductRegistry;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payment_registries")
public class PaymentRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_registry_id", nullable = false)
    private ProductRegistry productRegistry;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "interest_rate_amount", nullable = false, precision = 5, scale = 4)
    private BigDecimal interestRateAmount;

    @Column(name = "debt_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal debtAmount;

    @Column(nullable = false)
    private Boolean expired;

    @Column(name = "payment_expiration_date", nullable = false)
    private LocalDate paymentExpirationDate;

}