package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint12Entity;
import uz.jarvis.lines.entity.Checkpoint1Entity;

import java.util.List;
import java.util.Optional;

@Repository
public interface Checkpoint12Repository extends JpaRepository<Checkpoint12Entity, Long> {
  Optional<Checkpoint12Entity> findByComponentId(Long componentId);

  @Query("from Checkpoint12Entity where (component.code like ?1) or (component.name like ?1) ")
  List<Checkpoint12Entity> search(String searchQuery);

  List<Checkpoint12Entity> findByIsCreatableTrue();
  List<Checkpoint12Entity> findByIsCreatableFalse();
}