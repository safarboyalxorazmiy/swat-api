package uz.logist.components.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.logist.components.group.link.GroupLinkEntity;
import uz.logist.components.group.link.GroupLinkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentsGroupService {
  private final ComponentsGroupRepository componentsGroupRepository;
  private final GroupLinkRepository groupLinkRepository;

  public Boolean create(ComponentsGroupCreateDTO createDTO) {
    ComponentsGroupEntity componentsGroup = new ComponentsGroupEntity();
    componentsGroup.setCode(createDTO.getCode());
    componentsGroup.setCheckpoint(createDTO.getCheckpoint());
    componentsGroup.setName(createDTO.getName());
    componentsGroup.setUnit(createDTO.getUnit());
    componentsGroup.setSpecs(createDTO.getSpecs());
    componentsGroup.setStatus(createDTO.getStatus());
    componentsGroup.setPhoto(createDTO.getPhoto());
    componentsGroup.setWeight(createDTO.getWeight());

    ComponentsGroupEntity savedGroup = componentsGroupRepository.save(componentsGroup);
    System.out.println("Saved group id: " + savedGroup.getId());

    if (savedGroup.getId() != null) {
      List<Long> componentIds = createDTO.getComponentIds();
      for (Long componentId : componentIds) {
        GroupLinkEntity groupLink = new GroupLinkEntity();
        groupLink.setComponentGroupId(savedGroup.getId());
        groupLink.setComponentId(componentId);
        groupLinkRepository.save(groupLink);

        return true;

      }
    }

    return false;
  }
}