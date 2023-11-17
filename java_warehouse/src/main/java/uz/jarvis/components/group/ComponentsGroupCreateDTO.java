package uz.jarvis.components.group;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentsGroupCreateDTO {
  private Long compositeId;
  private Long componentId;
  private Double quantity;
}