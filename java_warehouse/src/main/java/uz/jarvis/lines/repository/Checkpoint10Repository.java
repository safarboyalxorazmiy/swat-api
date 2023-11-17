package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint10Entity;

import java.util.Optional;

@Repository
public interface Checkpoint10Repository extends JpaRepository<Checkpoint10Entity, Long> {
  Optional<Checkpoint10Entity> findByComponentId(Long componentId);
}