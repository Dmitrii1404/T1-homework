package my.project.accountProcessing.repository;

import my.project.accountProcessing.entity.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

}
