package uz.jarvis.components.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jarvis.components.group.dtos.CompositeComponentDeleteDTO;
import uz.jarvis.components.group.dtos.CompositeComponentEditDTO;
import uz.jarvis.components.group.dtos.CompositeComponentInfoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComponentsGroupService {
  private final ComponentsGroupRepository componentsGroupRepository;

  public List<CompositeComponentInfoDTO> getComponentsByCompositeId(Long compositeId) {
    List<ComponentsGroupEntity> byCompositeId = componentsGroupRepository.findByCompositeId(compositeId);
    List<CompositeComponentInfoDTO> result = new ArrayList<>();
    for (ComponentsGroupEntity group : byCompositeId) {
      CompositeComponentInfoDTO component = new CompositeComponentInfoDTO();
      component.setId(group.getComponent().getId());
      component.setCode(group.getComponent().getCode());
      component.setCheckpoint(group.getComponent().getCheckpoint());
      component.setQuantity(group.getQuantity());
      result.add(component);
    }

    return result;
  }

  public Boolean editCompositeComponent(CompositeComponentEditDTO editDTO) {
    Optional<ComponentsGroupEntity> byCompositeIdAndComponentId = componentsGroupRepository.findByCompositeIdAndComponentId(editDTO.getCompositeId(), editDTO.getComponentId());
    if (byCompositeIdAndComponentId.isEmpty()) {
      return false;
    }

    ComponentsGroupEntity componentsGroup = byCompositeIdAndComponentId.get();
    componentsGroup.setQuantity(editDTO.getQuantity());
    componentsGroupRepository.save(componentsGroup);
    return true;
  }

  public Boolean deleteCompositeComponent(CompositeComponentDeleteDTO deleteDTO) {
    Optional<ComponentsGroupEntity> byCompositeIdAndComponentId = componentsGroupRepository.findByCompositeIdAndComponentId(deleteDTO.getCompositeId(), deleteDTO.getComponentId());
    if (byCompositeIdAndComponentId.isEmpty()) {
      return false;
    }

    ComponentsGroupEntity componentsGroup = byCompositeIdAndComponentId.get();
    componentsGroupRepository.delete(componentsGroup);
    return true;
  }
}