package uz.jarvis.lines.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.jarvis.lines.entity.Checkpoint19Entity;
import uz.jarvis.lines.entity.Checkpoint1Entity;

import java.util.List;
import java.util.Optional;

@Repository
public interface Checkpoint19Repository extends JpaRepository<Checkpoint19Entity, Long> {
  Optional<Checkpoint19Entity> findByComponentId(Long componentId);

  @Query("from Checkpoint19Entity where (component.code like ?1) or (component.name like ?1) ")
  List<Checkpoint19Entity> search(String searchQuery);
}