package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint10Entity;
import uz.jarvis.lines.entity.Checkpoint1Entity;

import java.util.List;
import java.util.Optional;

@Repository
public interface Checkpoint10Repository extends JpaRepository<Checkpoint10Entity, Long> {
  Optional<Checkpoint10Entity> findByComponentId(Long componentId);

  @Query("from Checkpoint10Entity where (component.code like ?1) or (component.name like ?1)")
  List<Checkpoint10Entity> search(String searchQuery);

  List<Checkpoint10Entity> findByComponentIsMultipleTrue();

  List<Checkpoint10Entity> findByComponentIsMultipleFalse();
}