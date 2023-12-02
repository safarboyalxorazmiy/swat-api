package uz.jarvis.components;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ComponentInfoDTO {
  private Long id;
  private String code;
  private String name;
  private Long checkpoint;
  private String unit;
  private String specs;
  private Integer status;
  private String photo;
  private LocalDateTime time;
  private Integer type;
  private Double weight;
  private Double available;
  private String inner_code;
  private Integer income;
  private Boolean isMultiple;
}