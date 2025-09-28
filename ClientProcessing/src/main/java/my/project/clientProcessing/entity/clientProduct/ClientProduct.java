package my.project.clientProcessing.entity.clientProduct;

import jakarta.persistence.*;
import lombok.*;
import my.lib.core.StatusEnum;
import my.project.clientProcessing.entity.client.Client;
import my.project.clientProcessing.entity.product.Product;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "client_products")
public class ClientProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "open_date", nullable = false)
    private LocalDate openDate;

    @Column(name = "close_date", nullable = false)
    private LocalDate closeDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    @PrePersist
    public void prePersist() {
        if (this.openDate == null) {
            this.openDate = LocalDate.now();
        }
        if (this.closeDate == null) {
            this.closeDate = LocalDate.now().plusYears(1);
        }
        if (this.status == null) {
            this.status = StatusEnum.OPENED;
        }
    }
}