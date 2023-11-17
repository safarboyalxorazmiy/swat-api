package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint13Entity;
import uz.jarvis.lines.entity.Checkpoint1Entity;

import java.util.List;
import java.util.Optional;

@Repository
public interface Checkpoint13Repository extends JpaRepository<Checkpoint13Entity, Long> {
  Optional<Checkpoint13Entity> findByComponentId(Long componentId);

  @Query("from Checkpoint13Entity where (component.code like ?1) or (component.name like ?1) ")
  List<Checkpoint13Entity> search(String searchQuery);

  List<Checkpoint13Entity> findByComponentIsMultipleTrue();

  List<Checkpoint13Entity> findByComponentIsMultipleFalse();
}