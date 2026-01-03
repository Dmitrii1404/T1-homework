package my.project.accountProcessing.repository;


import my.lib.core.TransactionType;
import my.project.accountProcessing.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByAccountIdAndPaymentDateOrderByAmountAsc(Long accountId, LocalDate paymentDate);
    List<Payment> findByAccountIdAndTypeAndPayedAtIsNull(Long accountId, TransactionType type);

}
