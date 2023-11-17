package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint13Entity;

import java.util.Optional;

@Repository
public interface Checkpoint13Repository extends JpaRepository<Checkpoint13Entity, Long> {
  Optional<Checkpoint13Entity> findByComponentId(Long componentId);
}