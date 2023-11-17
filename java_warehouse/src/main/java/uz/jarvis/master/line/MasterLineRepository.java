package uz.jarvis.master.line;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterLineRepository extends JpaRepository<MasterLineEntity, Long> {
  Optional<MasterLineEntity> findByMasterId(Long masterId);
}