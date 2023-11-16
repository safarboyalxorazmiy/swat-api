package uz.logist.components.group;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComponentsGroupCreateDTO {
  private String code;
  private String name;
  private Long checkpoint;
  private String unit;
  private String specs;
  private Integer status;
  private String photo;
  private Double weight;
  private List<Long> componentIds;
}