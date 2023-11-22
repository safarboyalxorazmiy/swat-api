package uz.backall.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "models", schema = "public")
public class ModelEntity {
  @Id
  private Long id;

  private String name;

  private String code;

  private String comment;

  private Integer status;

  private LocalDateTime time;

  private String assembly;
}
