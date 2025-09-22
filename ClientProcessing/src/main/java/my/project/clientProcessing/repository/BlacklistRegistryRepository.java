package my.project.clientProcessing.repository;

import my.project.clientProcessing.entity.blacklistRegistry.BlacklistRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRegistryRepository extends JpaRepository<BlacklistRegistry, Long> {



}
