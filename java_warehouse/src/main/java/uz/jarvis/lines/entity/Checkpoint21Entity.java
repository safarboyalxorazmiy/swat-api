package uz.jarvis.lines.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.jarvis.components.ComponentsEntity;

@Getter
@Setter
@Entity
@Table(name = "21", schema = "checkpoints")
public class Checkpoint21Entity {
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

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isCreatable;
}