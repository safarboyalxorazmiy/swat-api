package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint11Entity;
import uz.jarvis.lines.entity.Checkpoint1Entity;

import java.util.List;
import java.util.Optional;

@Repository
public interface Checkpoint11Repository extends JpaRepository<Checkpoint11Entity, Long> {
  Optional<Checkpoint11Entity> findByComponentId(Long componentId);

  @Query("from Checkpoint11Entity where (component.code like ?1) or (component.name like ?1) ")
  List<Checkpoint11Entity> search(String searchQuery);
}