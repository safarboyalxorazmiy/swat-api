package uz.backall.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
  List<ModelEntity> findByNameLike(String name);
}