package my.project.clientProcessing.entity.product;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products",
    indexes = {
            @Index(name = "uk_product_productid", columnList = "product_id")
    })
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_seq")
    @SequenceGenerator(name = "products_seq", sequenceName = "products_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductKey key;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "product_id", nullable = false, unique = true)
    private String productId;

    @PrePersist
    public void prePersist() {
        if (this.createDate == null) {
            this.createDate = LocalDate.now();
        }
        if (this.id != null && (this.productId == null || this.productId.isBlank())) {
            this.productId = this.key.name() + this.id;
        }
    }
}