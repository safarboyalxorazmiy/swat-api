package uz.logist.components.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentsGroupRepository extends JpaRepository<ComponentsGroupEntity, Long> {
}