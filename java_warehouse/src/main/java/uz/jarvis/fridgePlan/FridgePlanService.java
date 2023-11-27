package uz.jarvis.fridgePlan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FridgePlanService {
  private final FridgePlanRepository fridgePlanRepository;

  public Boolean create(Long modelId, Long plan) {
    Optional<FridgePlanEntity> byModelId =
      fridgePlanRepository.findByModelId(modelId);

    if (byModelId.isPresent()) {
      FridgePlanEntity fridgePlanEntity = byModelId.get();
      fridgePlanEntity.setPlan(fridgePlanEntity.getPlan() + plan);
      fridgePlanRepository.save(fridgePlanEntity);
      return true;
    }

    FridgePlanEntity fridgePlanEntity = new FridgePlanEntity();
    fridgePlanEntity.setModelId(modelId);
    fridgePlanEntity.setPlan(plan);
    fridgePlanRepository.save(fridgePlanEntity);
    return true;
  }

  public Boolean update(Long modelId, Long plan) {
    Optional<FridgePlanEntity> byModelId =
        fridgePlanRepository.findByModelId(modelId);

    if (byModelId.isPresent()) {
      FridgePlanEntity fridgePlanEntity = byModelId.get();
      fridgePlanEntity.setPlan(plan);
      fridgePlanRepository.save(fridgePlanEntity);
      return true;
    }

    return false;
  }

  public Boolean delete(Long modelId, Long plan) {
    Optional<FridgePlanEntity> byModelId =
        fridgePlanRepository.findByModelId(modelId);

    if (byModelId.isPresent()) {
      FridgePlanEntity fridgePlanEntity = byModelId.get();
      fridgePlanRepository.delete(fridgePlanEntity);
      return true;
    }

    return null;
  }
}