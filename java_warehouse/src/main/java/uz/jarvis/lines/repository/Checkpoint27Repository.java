package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint1Entity;
import uz.jarvis.lines.entity.Checkpoint27Entity;

import java.util.List;
import java.util.Optional;

@Repository
public interface Checkpoint27Repository extends JpaRepository<Checkpoint27Entity, Long> {
  Optional<Checkpoint27Entity> findByComponentId(Long componentId);

  @Query("from Checkpoint27Entity where (component.code like ?1) or (component.name like ?1) ")
  List<Checkpoint27Entity> search(String searchQuery);

  List<Checkpoint27Entity> findByIsCreatableTrue();
  List<Checkpoint27Entity> findByIsCreatableFalse();
}