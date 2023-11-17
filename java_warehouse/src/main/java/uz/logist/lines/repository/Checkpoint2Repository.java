package uz.logist.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint2Entity;

import java.util.Optional;

@Repository
public interface Checkpoint2Repository extends JpaRepository<Checkpoint2Entity, Long> {
  Optional<Checkpoint2Entity> findByComponentId(Long componentId);
}