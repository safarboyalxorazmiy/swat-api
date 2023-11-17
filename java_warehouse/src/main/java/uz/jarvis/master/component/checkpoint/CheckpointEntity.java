package uz.jarvis.master.component.checkpoint;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "checkpoints")
public class CheckpointEntity {
  @Id
  private Integer id;
  private String name;
  private Boolean status;
  private String photo;
  private String address;
  private Boolean isCompositable;
}