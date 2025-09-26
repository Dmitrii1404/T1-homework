package my.project.creditProcessing.repository;

import my.project.creditProcessing.entity.paymentRegistry.PaymentRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PaymentRegistryRepository extends JpaRepository<PaymentRegistry, Long> {

    @Query("SELECT COALESCE(SUM(p.debtAmount), 0) FROM PaymentRegistry p JOIN p.productRegistry pr WHERE pr.clientId = :clientId")
    BigDecimal sumDebtByClientId(@Param("clientId") Long clientId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PaymentRegistry p JOIN p.productRegistry pr WHERE pr.clientId = :clientId AND p.expired = true")
    boolean existsExpiredPaymentByClientId(@Param("clientId") Long clientId);

}
