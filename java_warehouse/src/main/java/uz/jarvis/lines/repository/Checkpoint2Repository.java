package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint1Entity;
import uz.jarvis.lines.entity.Checkpoint2Entity;

import java.util.List;
import java.util.Optional;

@Repository
public interface Checkpoint2Repository extends JpaRepository<Checkpoint2Entity, Long> {
  Optional<Checkpoint2Entity> findByComponentId(Long componentId);

  @Query("from Checkpoint2Entity where (component.code like ?1) or (component.name like ?1)")
  List<Checkpoint2Entity> search(String searchQuery);

  List<Checkpoint2Entity> findByIsCreatableTrue();
  List<Checkpoint2Entity> findByIsCreatableFalse();
}