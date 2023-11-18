package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint1Entity;
import uz.jarvis.lines.entity.Checkpoint21Entity;

import java.util.List;
import java.util.Optional;

@Repository
public interface Checkpoint21Repository extends JpaRepository<Checkpoint21Entity, Long> {
  Optional<Checkpoint21Entity> findByComponentId(Long componentId);

  @Query("from Checkpoint21Entity where (component.code like ?1) or (component.name like ?1) ")
  List<Checkpoint21Entity> search(String searchQuery);

  List<Checkpoint21Entity> findByIsCreatableTrue();
  List<Checkpoint21Entity> findByIsCreatableFalse();
}