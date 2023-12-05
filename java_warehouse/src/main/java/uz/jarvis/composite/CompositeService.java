package uz.jarvis.composite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jarvis.components.ComponentsEntity;
import uz.jarvis.components.ComponentsRepository;
import uz.jarvis.components.group.ComponentsGroupEntity;
import uz.jarvis.components.group.ComponentsGroupRepository;
import uz.jarvis.lines.entity.*;
import uz.jarvis.lines.repository.*;
import uz.jarvis.master.component.dto.MasterComponentInfoDTO;
import uz.jarvis.master.line.MasterLineEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompositeService {
  private final ComponentsRepository componentsRepository;
  private final ComponentsGroupRepository componentsGroupRepository;

  private final Checkpoint1Repository checkpoint1Repository;
  private final Checkpoint2Repository checkpoint2Repository;
  private final Checkpoint9Repository checkpoint9Repository;
  private final Checkpoint10Repository checkpoint10Repository;
  private final Checkpoint11Repository checkpoint11Repository;
  private final Checkpoint12Repository checkpoint12Repository;
  private final Checkpoint13Repository checkpoint13Repository;
  private final Checkpoint19Repository checkpoint19Repository;
  private final Checkpoint20Repository checkpoint20Repository;
  private final Checkpoint21Repository checkpoint21Repository;
  private final Checkpoint27Repository checkpoint27Repository;

  public Boolean create(CompositeCreateDTO dto) {
    ComponentsEntity component = new ComponentsEntity(
      dto.getCode(),
      dto.getName(),
      dto.getCheckpoint(),
      dto.getUnit(),
      dto.getSpecs(),
      dto.getStatus(),
      dto.getPhoto(),
      LocalDateTime.now(),
      dto.getType(),
      dto.getWeight(),
      dto.getInner_code(),
      true
    );

    ComponentsEntity composite = componentsRepository.save(component);

    List<ComponentInfoDTO> components = dto.getComponents();
    for (ComponentInfoDTO componentInfoDTO : components) {
      ComponentsGroupEntity componentsGroup = new ComponentsGroupEntity();
      componentsGroup.setComponentId(componentInfoDTO.getComponentId());
      componentsGroup.setCompositeId(composite.getId());
      componentsGroup.setQuantity(componentInfoDTO.getQuantity());
      componentsGroupRepository.save(componentsGroup);
    }

    Integer checkpoint = dto.getCheckpoint();

    switch (checkpoint) {
      case 1 -> {
        Checkpoint1Entity entity = new Checkpoint1Entity();
        entity.setComponentId(composite.getId());
        entity.setQuantity(0.0);
        entity.setIsCreatable(true);
        checkpoint1Repository.save(entity);

        for (ComponentInfoDTO info : dto.getComponents()) {
          Optional<Checkpoint1Entity> byComponentId =
            checkpoint1Repository.findByComponentId(info.getComponentId());
          if (byComponentId.isEmpty()) {
            entity = new Checkpoint1Entity();
            entity.setComponentId(info.getComponentId());
            entity.setQuantity(0.0);
            entity.setIsCreatable(false);
            checkpoint1Repository.save(entity);
          }
        }
        return true;
      }
      case 2 -> {
        Checkpoint2Entity entity = new Checkpoint2Entity();
        entity.setComponentId(composite.getId());
        entity.setQuantity(0.0);
        entity.setIsCreatable(true);
        checkpoint2Repository.save(entity);

        for (ComponentInfoDTO info : dto.getComponents()) {
          Optional<Checkpoint2Entity> byComponentId = checkpoint2Repository.findByComponentId(info.getComponentId());
          if (byComponentId.isEmpty()) {
            entity = new Checkpoint2Entity();
            entity.setComponentId(info.getComponentId());
            entity.setQuantity(0.0);
            entity.setIsCreatable(false);
            checkpoint2Repository.save(entity);
          }
        }
        return true;
      }
      case 9 -> {
        Checkpoint9Entity entity = new Checkpoint9Entity();
        entity.setComponentId(composite.getId());
        entity.setQuantity(0.0);
        entity.setIsCreatable(true);
        checkpoint9Repository.save(entity);

        for (ComponentInfoDTO info : dto.getComponents()) {
          Optional<Checkpoint9Entity> byComponentId = checkpoint9Repository.findByComponentId(info.getComponentId());
          if (byComponentId.isEmpty()) {
            entity = new Checkpoint9Entity();
            entity.setComponentId(info.getComponentId());
            entity.setQuantity(0.0);
            entity.setIsCreatable(false);
            checkpoint9Repository.save(entity);
          }
        }
        return true;
      }
      case 10 -> {
        Checkpoint10Entity entity = new Checkpoint10Entity();
        entity.setComponentId(composite.getId());
        entity.setQuantity(0.0);
        entity.setIsCreatable(true);
        checkpoint10Repository.save(entity);

        for (ComponentInfoDTO info : dto.getComponents()) {
          Optional<Checkpoint10Entity> byComponentId =
            checkpoint10Repository.findByComponentId(info.getComponentId());
          if (byComponentId.isEmpty()) {
            entity = new Checkpoint10Entity();
            entity.setComponentId(info.getComponentId());
            entity.setQuantity(0.0);
            entity.setIsCreatable(false);
            checkpoint10Repository.save(entity);
          }
        }
        return true;
      }
      case 11 -> {
        Checkpoint11Entity entity = new Checkpoint11Entity();
        entity.setComponentId(composite.getId());
        entity.setQuantity(0.0);
        entity.setIsCreatable(true);
        checkpoint11Repository.save(entity);

        for (ComponentInfoDTO info : dto.getComponents()) {
          Optional<Checkpoint11Entity> byComponentId = checkpoint11Repository.findByComponentId(info.getComponentId());
          if (byComponentId.isEmpty()) {
            entity = new Checkpoint11Entity();
            entity.setComponentId(info.getComponentId());
            entity.setQuantity(0.0);
            entity.setIsCreatable(false);
            checkpoint11Repository.save(entity);
          }
        }
        return true;
      }
      case 12 -> {
        Checkpoint12Entity entity = new Checkpoint12Entity();
        entity.setComponentId(composite.getId());
        entity.setQuantity(0.0);
        entity.setIsCreatable(true);
        checkpoint12Repository.save(entity);

        for (ComponentInfoDTO info : dto.getComponents()) {
          Optional<Checkpoint12Entity> byComponentId =
            checkpoint12Repository.findByComponentId(info.getComponentId());
          if (byComponentId.isEmpty()) {
            entity = new Checkpoint12Entity();
            entity.setComponentId(info.getComponentId());
            entity.setQuantity(0.0);
            entity.setIsCreatable(false);
            checkpoint12Repository.save(entity);
          }
        }
        return true;
      }
      case 13 -> {
        Checkpoint13Entity entity = new Checkpoint13Entity();
        entity.setComponentId(composite.getId());
        entity.setQuantity(0.0);
        entity.setIsCreatable(true);
        checkpoint13Repository.save(entity);

        for (ComponentInfoDTO info : dto.getComponents()) {
          Optional<Checkpoint13Entity> byComponentId =
            checkpoint13Repository.findByComponentId(info.getComponentId());
          if (byComponentId.isEmpty()) {
            entity = new Checkpoint13Entity();
            entity.setComponentId(info.getComponentId());
            entity.setQuantity(0.0);
            entity.setIsCreatable(false);
            checkpoint13Repository.save(entity);
          }
        }
        return true;
      }
      case 19 -> {
        Checkpoint19Entity entity = new Checkpoint19Entity();
        entity.setComponentId(composite.getId());
        entity.setQuantity(0.0);
        entity.setIsCreatable(true);
        checkpoint19Repository.save(entity);

        for (ComponentInfoDTO info : dto.getComponents()) {
          Optional<Checkpoint19Entity> byComponentId =
            checkpoint19Repository.findByComponentId(info.getComponentId());
          if (byComponentId.isEmpty()) {
            entity = new Checkpoint19Entity();
            entity.setComponentId(info.getComponentId());
            entity.setQuantity(0.0);
            entity.setIsCreatable(false);
            checkpoint19Repository.save(entity);
          }
        }

        return true;
      }
      case 20 -> {
        Checkpoint20Entity entity = new Checkpoint20Entity();
        entity.setComponentId(composite.getId());
        entity.setQuantity(0.0);
        entity.setIsCreatable(true);
        checkpoint20Repository.save(entity);

        for (ComponentInfoDTO info : dto.getComponents()) {
          Optional<Checkpoint20Entity> byComponentId =
            checkpoint20Repository.findByComponentId(info.getComponentId());
          if (byComponentId.isEmpty()) {
            entity = new Checkpoint20Entity();
            entity.setComponentId(info.getComponentId());
            entity.setQuantity(0.0);
            entity.setIsCreatable(false);
            checkpoint20Repository.save(entity);
          }
        }
        return true;
      }
      case 21 -> {
        Checkpoint21Entity entity = new Checkpoint21Entity();
        entity.setComponentId(composite.getId());
        entity.setQuantity(0.0);
        entity.setIsCreatable(true);
        checkpoint21Repository.save(entity);

        for (ComponentInfoDTO info : dto.getComponents()) {
          Optional<Checkpoint21Entity> byComponentId =
            checkpoint21Repository.findByComponentId(info.getComponentId());
          if (byComponentId.isEmpty()) {
            entity = new Checkpoint21Entity();
            entity.setComponentId(info.getComponentId());
            entity.setQuantity(0.0);
            entity.setIsCreatable(false);
            checkpoint21Repository.save(entity);
          }
        }
        return true;
      }
      case 27 -> {
        Checkpoint27Entity entity = new Checkpoint27Entity();
        entity.setComponentId(composite.getId());
        entity.setQuantity(0.0);
        entity.setIsCreatable(true);
        checkpoint27Repository.save(entity);

        for (ComponentInfoDTO info : dto.getComponents()) {
          Optional<Checkpoint27Entity> byComponentId =
            checkpoint27Repository.findByComponentId(info.getComponentId());
          if (byComponentId.isEmpty()) {
            entity = new Checkpoint27Entity();
            entity.setComponentId(info.getComponentId());
            entity.setQuantity(0.0);
            entity.setIsCreatable(false);
            checkpoint27Repository.save(entity);
          }
        }
        return true;
      }
    }

    return false;
  }
}
