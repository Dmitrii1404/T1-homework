package my.project.clientProcessing.repository;

import my.project.clientProcessing.entity.blacklistRegistry.BlacklistRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRepository extends JpaRepository<BlacklistRegistry, Long> {

    boolean existsByDocumentId(String documentId);

}
