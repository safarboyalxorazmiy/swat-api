package uz.jarvis.components.group;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.jarvis.components.ComponentsEntity;

@Getter
@Setter

@Entity
@Table(name = "component_group")
public class ComponentsGroupEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "composite_id")
  private Long compositeId;

  @ManyToOne
  @JoinColumn(name = "composite_id", insertable = false, updatable = false)
  private ComponentsEntity composite;

  @Column(name = "component_id")
  private Long componentId;

  @ManyToOne
  @JoinColumn(name = "component_id", insertable = false, updatable = false)
  private ComponentsEntity component;

  private Double quantity;
}