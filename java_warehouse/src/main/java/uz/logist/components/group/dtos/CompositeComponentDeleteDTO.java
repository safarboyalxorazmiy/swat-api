package uz.logist.components.group.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompositeComponentDeleteDTO {
  private Long compositeId;
  private Long componentId;
}