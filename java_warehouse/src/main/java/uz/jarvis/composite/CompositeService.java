package uz.jarvis.composite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jarvis.components.ComponentsEntity;
import uz.jarvis.components.ComponentsRepository;
import uz.jarvis.components.group.ComponentsGroupEntity;
import uz.jarvis.components.group.ComponentsGroupRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompositeService {
  private final ComponentsRepository componentsRepository;
  private final ComponentsGroupRepository componentsGroupRepository;

  public Boolean create(CompositeCreateDTO dto) {
    ComponentsEntity component = new ComponentsEntity(
        dto.getCode(),
        dto.getName(),
        dto.getCheckpoint(),
        dto.getUnit(),
        dto.getSpecs(),
        dto.getStatus(),
        dto.getPhoto(),
        LocalDateTime.now(),
        dto.getType(),
        dto.getWeight(),
        dto.getAvailable(),
        dto.getInner_code(),
        dto.getIncome(),
        true);

    ComponentsEntity composite = componentsRepository.save(component);

    List<ComponentInfoDTO> components = dto.getComponents();
    for (ComponentInfoDTO componentInfoDTO : components) {
      ComponentsGroupEntity componentsGroup = new ComponentsGroupEntity();
      componentsGroup.setComponentId(componentInfoDTO.getComponentId());
      componentsGroup.setCompositeId(composite.getId());
      componentsGroup.setQuantity(componentInfoDTO.getQuantity());
      componentsGroupRepository.save(componentsGroup);
    }

    return true;
  }
}
