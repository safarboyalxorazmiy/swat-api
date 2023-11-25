package uz.jarvis.fridgePlan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FridgePlanRepository extends JpaRepository<FridgePlanEntity, Long> {
  Optional<FridgePlanEntity> findByModelId(Long modelId);
}