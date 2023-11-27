package uz.jarvis.fridgePlan;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.jarvis.models.ModelEntity;
import uz.jarvis.user.User;

@ToString
@Getter
@Setter
@Entity
@Table(name = "fridge_plan")
public class FridgePlanEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "model_id", unique = true)
  private Long modelId;

  @ManyToOne
  @JoinColumn(name = "model_id", insertable = false, updatable = false)
  private ModelEntity model;

  @Column
  private Long plan;
}