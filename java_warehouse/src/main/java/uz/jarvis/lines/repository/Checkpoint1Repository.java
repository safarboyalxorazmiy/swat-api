package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint1Entity;

import java.util.List;
import java.util.Optional;

@Repository
public interface Checkpoint1Repository extends JpaRepository<Checkpoint1Entity, Long> {
  Optional<Checkpoint1Entity> findByComponentId(Long componentId);

  @Query("from Checkpoint1Entity where (component.code like ?1) or (component.name like ?1)")
  List<Checkpoint1Entity> search(String searchQuery);

  List<Checkpoint1Entity> findByIsCreatableTrue();
  List<Checkpoint1Entity> findByIsCreatableFalse();
}