package uz.jarvis.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentsService {
  private final ComponentsRepository componentsRepository;

  public List<CompositeDTO> getComposites() {
    List<ComponentsEntity> byIsMultipleTrue = componentsRepository.findByIsMultipleTrue();
    List<CompositeDTO> result = new ArrayList<>();
    for (ComponentsEntity component : byIsMultipleTrue) {
      CompositeDTO composite = new CompositeDTO();
      composite.setId(component.getId());
      composite.setCheckpoint(component.getCheckpoint());
      composite.setCode(component.getCode());
      composite.setName(component.getName());
      result.add(composite);
    }

    return result;
  }

  public List<ComponentInfoDTO> getComponents() {
    List<ComponentsEntity> byIsMultipleTrue = componentsRepository.findByIsMultipleFalse();
    List<ComponentInfoDTO> result = new ArrayList<>();
    for (ComponentsEntity entity : byIsMultipleTrue) {
      ComponentInfoDTO dto = new ComponentInfoDTO(
          entity.getId(),
          entity.getCode(),
          entity.getName(),
          entity.getCheckpoint(),
          entity.getUnit(), entity.getSpecs(),
          entity.getStatus(), entity.getPhoto(),
          entity.getTime(), entity.getType(),
          entity.getWeight(), entity.getAvailable(),
          entity.getInner_code(), entity.getIncome(),
          entity.getIsMultiple()
      );
      result.add(dto);
    }

    return result;
  }
}
