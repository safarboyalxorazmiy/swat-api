package uz.logist.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint20Entity;

import java.util.Optional;

@Repository
public interface Checkpoint20Repository extends JpaRepository<Checkpoint20Entity, Long> {
  Optional<Checkpoint20Entity> findByComponentId(Long componentId);
}