package uz.jarvis.components;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "components")
@RequiredArgsConstructor
public class ComponentsEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String code;

  @Column
  private String name;

  @Column
  private Integer checkpoint;

  @Column
  private String unit;

  @Column
  private String specs;

  @Column
  private Integer status;

  @Column
  private String photo;

  @Column
  private LocalDateTime time;

  @Column
  private Integer type;

  @Column
  private Double weight;

  @Column
  private Double available = 0.0;

  @Column
  private String inner_code;

  @Column
  private Integer income = 0;

  @Column(nullable = false, columnDefinition = "boolean default false", updatable = true)
  private Boolean isMultiple;

  public ComponentsEntity(String code, String name, Integer checkpoint, String unit, String specs, Integer status, String photo, LocalDateTime time, Integer type, Double weight, String inner_code, Boolean isMultiple) {
    this.code = code;
    this.name = name;
    this.checkpoint = checkpoint;
    this.unit = unit;
    this.specs = specs;
    this.status = status;
    this.photo = photo;
    this.time = time;
    this.type = type;
    this.weight = weight;
    this.inner_code = inner_code;
    this.isMultiple = isMultiple;
  }
}