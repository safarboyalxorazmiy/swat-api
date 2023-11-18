package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint1Entity;
import uz.jarvis.lines.entity.Checkpoint20Entity;

import java.util.List;
import java.util.Optional;

@Repository
public interface Checkpoint20Repository extends JpaRepository<Checkpoint20Entity, Long> {
  Optional<Checkpoint20Entity> findByComponentId(Long componentId);

  @Query("from Checkpoint20Entity where (component.code like ?1) or (component.name like ?1) ")
  List<Checkpoint20Entity> search(String searchQuery);

  List<Checkpoint20Entity> findByIsCreatableTrue();
  List<Checkpoint20Entity> findByIsCreatableFalse();
}