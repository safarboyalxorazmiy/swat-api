package uz.logist.components.group;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.logist.components.ComponentsEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "component_group")
public class ComponentsGroupEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String code;

  @Column
  private String name;

  @Column
  private Long checkpoint;

  @Column
  private String unit;

  @Column
  private String specs;

  @Column
  private Integer status;

  @Column
  private String photo;

  @Column
  private LocalDateTime createdDate = LocalDateTime.now();

  @Column
  private Double weight;
}