package uz.jarvis.logist.component;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.jarvis.components.ComponentsEntity;
import uz.jarvis.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "logist_component")
public class LogistComponentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "component_id")
  private Long componentId;

  @ManyToOne
  @JoinColumn(name = "component_id", insertable = false, updatable = false)
  private ComponentsEntity component;

  @Column(name = "logist_id")
  private Long logistId;

  @ManyToOne
  @JoinColumn(name = "logist_id", insertable = false, updatable = false)
  private User logist;

  @Column
  private Double quantity;

  @Column
  private LocalDateTime updatedDate;
}