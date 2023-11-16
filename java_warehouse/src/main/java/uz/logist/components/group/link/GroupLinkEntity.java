package uz.logist.components.group.link;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "group_link")
public class GroupLinkEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "component_group_id")
  private Long componentGroupId;

  @Column(name = "component_id")
  private Long componentId;
}
