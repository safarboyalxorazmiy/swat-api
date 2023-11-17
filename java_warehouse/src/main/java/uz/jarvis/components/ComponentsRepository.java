package uz.jarvis.components;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentsRepository extends JpaRepository<ComponentsEntity, Long> {
  List<ComponentsEntity> findByIsMultipleTrue();
}