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
}
