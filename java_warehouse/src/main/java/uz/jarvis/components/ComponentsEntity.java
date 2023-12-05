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
  private LocalDateTime time;

  @Column
  private Integer type;

  @Column
  private Double weight;

  @Column
  private Double available;

  @Column
  private String inner_code;

  @Column
  private Integer income;

  @Column(nullable = false, columnDefinition = "boolean default false", updatable = true)
  private Boolean isMultiple;

  public ComponentsEntity(String code, String name, Long checkpoint, String unit, String specs, Integer status, String photo, LocalDateTime time, Integer type, Double weight, Double available, String inner_code, Integer income, Boolean isMultiple) {
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
    this.available = available;
    this.inner_code = inner_code;
    this.income = income;
    this.isMultiple = isMultiple;
  }
}