package uz.jarvis.master.component.checkpoint;

import jakarta.persistence.Column;
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

  @Column
  private String name;

  @Column
  private Boolean status;

  @Column
  private String photo;

  @Column
  private String address;

  @Column(nullable = false, columnDefinition = "boolean default false", updatable = true)
  private Boolean isCompositeCreatable;
}