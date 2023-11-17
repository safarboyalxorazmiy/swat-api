package uz.logist.master.component;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.jarvis.components.ComponentsEntity;
import uz.jarvis.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "master_component_request")
public class MasterComponentRequestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "component_id")
  private Long componentId;

  @ManyToOne
  @JoinColumn(name = "component_id", insertable = false, updatable = false)
  private ComponentsEntity component;

  @Column
  private Double quantity;

  @Column(name = "master_id")
  private Long masterId;

  @ManyToOne
  @JoinColumn(name = "master_id", insertable = false, updatable = false)
  private User master;

  @Column(name = "logist_id")
  private Long logistId;

  @ManyToOne
  @JoinColumn(name = "logist_id", insertable = false, updatable = false)
  private User logist;

  @Column
  private Boolean verified;

  @Column
  private LocalDateTime createdDate = LocalDateTime.now();

}