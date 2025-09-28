package my.project.accountProcessing.repository;

import my.project.accountProcessing.entity.card.Card;
import my.project.accountProcessing.entity.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select count(t) from Transaction t where t.card = :card and t.timestamp >= :after")
    long countByCardAndTimestampAfter(@Param("card") Card card, @Param("after") LocalDateTime after);

}
