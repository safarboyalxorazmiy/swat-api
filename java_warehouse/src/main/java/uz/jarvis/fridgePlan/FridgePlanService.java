package uz.jarvis.fridgePlan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    return false;
  }

  public List<FridgePlanInfoDTO> findAll() {
    List<FridgePlanEntity> all = fridgePlanRepository.findAll();
    List<FridgePlanInfoDTO> result = new ArrayList<>();

    for (FridgePlanEntity fridgePlanEntity : all) {
      FridgePlanInfoDTO info = new FridgePlanInfoDTO();
      info.setId(fridgePlanEntity.getId());
      info.setPlan(fridgePlanEntity.getPlan());
      info.setModelId(fridgePlanEntity.getModelId());

      info.setModelName(fridgePlanEntity.getModel().getName());
      info.setModelCode(fridgePlanEntity.getModel().getCode());
      info.setModelComment(fridgePlanEntity.getModel().getComment());

      result.add(info);
    }

    return result;
  }
}