package uz.logist.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint11Entity;

import java.util.Optional;

@Repository
public interface Checkpoint11Repository extends JpaRepository<Checkpoint11Entity, Long> {
  Optional<Checkpoint11Entity> findByComponentId(Long componentId);
}