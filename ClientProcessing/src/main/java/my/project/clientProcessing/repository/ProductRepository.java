package my.project.clientProcessing.repository;

import my.project.clientProcessing.entity.product.Product;
import my.project.clientProcessing.entity.product.ProductKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllProductsByKey(Pageable pageable, ProductKey productKey);

}
