package uz.jarvis.composite;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.jarvis.components.group.ComponentsGroupCreateDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class CompositeCreateDTO {
  private String code;
  private String name;
  private Long checkpoint;
  private String unit;
  private String specs;
  private Integer status;
  private String photo;
  private Integer type;
  private Double weight;
  private Double available;
  private String inner_code;
  private Integer income;

  private List<CompositeComponentCreateDTO> components;
}