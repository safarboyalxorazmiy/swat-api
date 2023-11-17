package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint1Entity;

import java.util.Optional;

@Repository
public interface Checkpoint1Repository extends JpaRepository<Checkpoint1Entity, Long> {
  Optional<Checkpoint1Entity> findByComponentId(Long componentId);
}