package my.project.creditProcessing.entity.productRegistry;

import jakarta.persistence.*;
import lombok.*;
import my.project.creditProcessing.entity.paymentRegistry.PaymentRegistry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_registries",
    indexes = {
            @Index(name = "idx_product_registries_client_id", columnList = "client_id")
    })
public class ProductRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 4)
    private BigDecimal interestRate;

    @Column(name = "open_date", nullable = false)
    private LocalDate openDate;

    @Column(name = "month_count", nullable = false)
    private Integer monthCount;

    @OneToMany(mappedBy = "productRegistry", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PaymentRegistry> payments;

}