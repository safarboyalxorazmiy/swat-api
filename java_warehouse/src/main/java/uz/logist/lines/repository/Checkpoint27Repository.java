package uz.logist.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint27Entity;

import java.util.Optional;

@Repository
public interface Checkpoint27Repository extends JpaRepository<Checkpoint27Entity, Long> {
  Optional<Checkpoint27Entity> findByComponentId(Long componentId);
}