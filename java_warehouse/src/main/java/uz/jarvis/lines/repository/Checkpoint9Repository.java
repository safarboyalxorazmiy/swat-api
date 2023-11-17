package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint9Entity;

import java.util.Optional;

@Repository
public interface Checkpoint9Repository extends JpaRepository<Checkpoint9Entity, Long> {
  Optional<Checkpoint9Entity> findByComponentId(Long componentId);
}