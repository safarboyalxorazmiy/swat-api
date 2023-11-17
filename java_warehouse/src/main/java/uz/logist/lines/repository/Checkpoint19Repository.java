package uz.logist.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint19Entity;

import java.util.Optional;

@Repository
public interface Checkpoint19Repository extends JpaRepository<Checkpoint19Entity, Long> {
  Optional<Checkpoint19Entity> findByComponentId(Long componentId);
}