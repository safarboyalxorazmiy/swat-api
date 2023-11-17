package uz.jarvis.components.group.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompositeComponentEditDTO {
  private Long compositeId;
  private Long componentId;
  private Double quantity;
}