package uz.jarvis.components.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComponentsGroupRepository extends JpaRepository<ComponentsGroupEntity, Long> {
  List<ComponentsGroupEntity> findByCompositeId(Long compositeId);

  Optional<ComponentsGroupEntity> findByCompositeIdAndComponentId(Long compositeId, Long componentId);
}