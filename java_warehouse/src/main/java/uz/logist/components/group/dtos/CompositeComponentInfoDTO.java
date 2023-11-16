package uz.logist.components.group.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompositeComponentInfoDTO {
  private Long id;
  private String code;
  private String name;
  private Long checkpoint;
  private Double quantity;
}