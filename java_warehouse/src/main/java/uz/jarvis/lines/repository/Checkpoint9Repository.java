package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint1Entity;
import uz.jarvis.lines.entity.Checkpoint9Entity;

import java.util.List;
import java.util.Optional;

@Repository
public interface Checkpoint9Repository extends JpaRepository<Checkpoint9Entity, Long> {
  Optional<Checkpoint9Entity> findByComponentId(Long componentId);

  @Query("from Checkpoint9Entity where (component.code like ?1) or (component.name like ?1)")
  List<Checkpoint9Entity> search(String searchQuery);

  List<Checkpoint9Entity> findByComponentIsMultipleTrue();

  List<Checkpoint9Entity> findByComponentIsMultipleFalse();
}