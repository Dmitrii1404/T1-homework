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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductKey key;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Transient
    public String getProductId() {
        return key.name() + this.getId();
    }
}