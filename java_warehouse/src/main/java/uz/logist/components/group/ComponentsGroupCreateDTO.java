package uz.logist.components.group;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComponentsGroupCreateDTO {
  private Long compositeId;
  private List<Long> componentIds;
}