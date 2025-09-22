package my.project.clientProcessing.repository;

import my.project.clientProcessing.entity.clientProduct.ClientProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProductRepository extends JpaRepository<ClientProduct, Long> {



}
