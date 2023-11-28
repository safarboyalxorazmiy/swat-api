package uz.jarvis.master.component.checkpoint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckpointInfoDTO {
  private Integer id;
  private String name;
  private Boolean status;
  private String photo;
  private String address;
  private Boolean isCompositeCreatable;
}