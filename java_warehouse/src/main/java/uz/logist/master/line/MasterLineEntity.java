package uz.logist.master.line;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.jarvis.user.User;

@Getter
@Setter
@Entity
@Table(name = "master_line")
public class MasterLineEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer lineId;

  @Column(name = "master_id")
  private Long masterId;

  @ManyToOne
  @JoinColumn(name = "master_id", insertable = false, updatable = false)
  private User user;
}