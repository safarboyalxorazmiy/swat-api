package uz.logist.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint21Entity;

import java.util.Optional;

@Repository
public interface Checkpoint21Repository extends JpaRepository<Checkpoint21Entity, Long> {
  Optional<Checkpoint21Entity> findByComponentId(Long componentId);
}