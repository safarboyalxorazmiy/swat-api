package uz.jarvis.fridgePlan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FridgePlanService {
  private final FridgePlanRepository fridgePlanRepository;

  public void create(Long modelId, Long plan) {
    Optional<FridgePlanEntity> byModelId =
      fridgePlanRepository.findByModelId(modelId);

    if (byModelId.isPresent()) {
      FridgePlanEntity fridgePlanEntity = byModelId.get();
      fridgePlanEntity.setPlan(fridgePlanEntity.getPlan() + plan);
      fridgePlanRepository.save(fridgePlanEntity);
      return;
    }

    FridgePlanEntity fridgePlanEntity = new FridgePlanEntity();
    fridgePlanEntity.setModelId(modelId);
    fridgePlanEntity.setPlan(plan);
    fridgePlanRepository.save(fridgePlanEntity);
  }

  public void update(Long modelId, Long plan) {
    Optional<FridgePlanEntity> byModelId =
      fridgePlanRepository.findByModelId(modelId);

    if (byModelId.isPresent()) {
      FridgePlanEntity fridgePlanEntity = byModelId.get();
      fridgePlanEntity.setPlan(plan);
      fridgePlanRepository.save(fridgePlanEntity);
      return;
    }
  }

  public void delete(Long modelId) {
    Optional<FridgePlanEntity> byModelId =
      fridgePlanRepository.findByModelId(modelId);

    if (byModelId.isPresent()) {
      FridgePlanEntity fridgePlanEntity = byModelId.get();
      fridgePlanRepository.delete(fridgePlanEntity);
      return;
    }
  }

  public List<FridgePlanEntity> findAll() {
    return fridgePlanRepository.findAll();
  }
}