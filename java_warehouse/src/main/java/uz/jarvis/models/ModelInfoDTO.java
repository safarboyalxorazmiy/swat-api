package uz.jarvis.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ModelInfoDTO {
  private Long id;
  private String name;
  private String code;
  private String comment;
  private Integer status;
  private LocalDateTime time;
  private String assembly;
}
