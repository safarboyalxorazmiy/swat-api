package uz.logist.components.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentsGroupService {
    private final ComponentsGroupRepository componentsGroupRepository;

    public Boolean create(ComponentsGroupCreateDTO createDTO) {
        List<Long> componentIds = createDTO.getComponentIds();
        for (Long componentId : componentIds) {
            ComponentsGroupEntity componentsGroup = new ComponentsGroupEntity();
            componentsGroup.setComposite(createDTO.getCompositeId());
            componentsGroup.setComponentId(componentId);
            componentsGroupRepository.save(componentsGroup);
        }

        return true;
    }


}