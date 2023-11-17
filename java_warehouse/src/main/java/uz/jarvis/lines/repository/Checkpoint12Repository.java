package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint12Entity;

import java.util.Optional;

@Repository
public interface Checkpoint12Repository extends JpaRepository<Checkpoint12Entity, Long> {
  Optional<Checkpoint12Entity> findByComponentId(Long componentId);
}