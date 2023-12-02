package uz.jarvis.master.composite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jarvis.components.group.ComponentsGroupEntity;
import uz.jarvis.components.group.ComponentsGroupRepository;
import uz.jarvis.lines.entity.*;
import uz.jarvis.lines.repository.*;
import uz.jarvis.master.component.dto.MasterCompositeCreateDTO;
import uz.jarvis.master.component.exception.QuantityNotEnoughException;
import uz.jarvis.master.line.MasterLineEntity;
import uz.jarvis.master.line.MasterLineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MasterCompositeService {
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

  private final ComponentsGroupRepository componentsGroupRepository;
  private final MasterLineRepository masterLineRepository;

  public Boolean createComposite(MasterCompositeCreateDTO dto, Long fromMasterId) {
    Optional<MasterLineEntity> byMasterId =
      masterLineRepository.findByMasterId(fromMasterId);
    if (byMasterId.isEmpty()) {
      return false;
    }

    Long compositeId = dto.getCompositeId();
    List<ComponentsGroupEntity> byCompositeId = componentsGroupRepository.findByCompositeId(compositeId);

    MasterLineEntity masterLineEntity = byMasterId.get();
    Integer lineId = masterLineEntity.getLineId();
    boolean result = false;
    switch (lineId) {
      case 1 -> {
        List<Checkpoint1Entity> savedComponentQuantities =
          new ArrayList<>();

        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint1Entity> byComponentId =
            checkpoint1Repository.findByComponentId(
              componentsGroupEntity.getComponentId()
            );

          if (byComponentId.isEmpty()) {
            break;
          }

          Checkpoint1Entity checkpointEntity = byComponentId.get();

          double subtractedQuantity =
            checkpointEntity.getQuantity() - componentsGroupEntity.getQuantity();

          if (subtractedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(subtractedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        Optional<Checkpoint1Entity> byComponentId =
          checkpoint1Repository.findByComponentId(compositeId);

        if (byComponentId.isEmpty()) {
          break;
        }

        Checkpoint1Entity composite = byComponentId.get();
        composite.setQuantity(
          composite.getQuantity() + dto.getQuantity()
        );
        savedComponentQuantities.add(composite);

        checkpoint1Repository.saveAll(savedComponentQuantities);
        result = true;
      }

      case 2 -> {
        List<Checkpoint2Entity> savedComponentQuantities =
          new ArrayList<>();

        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint2Entity> byComponentId =
            checkpoint2Repository.findByComponentId(
              componentsGroupEntity.getComponentId()
            );

          if (byComponentId.isEmpty()) {
            break;
          }

          Checkpoint2Entity checkpointEntity = byComponentId.get();

          double subtractedQuantity =
            checkpointEntity.getQuantity() - componentsGroupEntity.getQuantity();

          if (subtractedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(subtractedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        Optional<Checkpoint2Entity> byComponentId =
          checkpoint2Repository.findByComponentId(compositeId);

        if (byComponentId.isEmpty()) {
          break;
        }

        Checkpoint2Entity composite = byComponentId.get();
        composite.setQuantity(
          composite.getQuantity() + dto.getQuantity()
        );
        savedComponentQuantities.add(composite);

        checkpoint2Repository.saveAll(savedComponentQuantities);
        result = true;
      }

      case 9 -> {
        List<Checkpoint9Entity> savedComponentQuantities =
          new ArrayList<>();

        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint9Entity> byComponentId =
            checkpoint9Repository.findByComponentId(
              componentsGroupEntity.getComponentId()
            );

          if (byComponentId.isEmpty()) {
            break;
          }

          Checkpoint9Entity checkpointEntity = byComponentId.get();

          double subtractedQuantity =
            checkpointEntity.getQuantity() - componentsGroupEntity.getQuantity();

          if (subtractedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(subtractedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        Optional<Checkpoint9Entity> byComponentId =
          checkpoint9Repository.findByComponentId(compositeId);

        if (byComponentId.isEmpty()) {
          break;
        }

        Checkpoint9Entity composite = byComponentId.get();
        composite.setQuantity(
          composite.getQuantity() + dto.getQuantity()
        );
        savedComponentQuantities.add(composite);

        checkpoint9Repository.saveAll(savedComponentQuantities);
        result = true;
      }

      case 10 -> {
        List<Checkpoint10Entity> savedComponentQuantities =
          new ArrayList<>();

        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint10Entity> byComponentId =
            checkpoint10Repository.findByComponentId(
              componentsGroupEntity.getComponentId()
            );

          if (byComponentId.isEmpty()) {
            break;
          }

          Checkpoint10Entity checkpointEntity = byComponentId.get();

          double subtractedQuantity =
            checkpointEntity.getQuantity() - componentsGroupEntity.getQuantity();

          if (subtractedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(subtractedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        Optional<Checkpoint10Entity> byComponentId =
          checkpoint10Repository.findByComponentId(compositeId);

        if (byComponentId.isEmpty()) {
          break;
        }

        Checkpoint10Entity composite = byComponentId.get();
        composite.setQuantity(composite.getQuantity() + dto.getQuantity());
        savedComponentQuantities.add(composite);

        checkpoint10Repository.saveAll(savedComponentQuantities);
        result = true;
      }

      case 11 -> {
        List<Checkpoint11Entity> savedComponentQuantities =
          new ArrayList<>();

        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint11Entity> byComponentId =
            checkpoint11Repository.findByComponentId(
              componentsGroupEntity.getComponentId()
            );

          if (byComponentId.isEmpty()) {
            break;
          }

          Checkpoint11Entity checkpointEntity = byComponentId.get();

          double subtractedQuantity =
            checkpointEntity.getQuantity() - componentsGroupEntity.getQuantity();

          if (subtractedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(subtractedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        Optional<Checkpoint11Entity> byComponentId =
          checkpoint11Repository.findByComponentId(compositeId);

        if (byComponentId.isEmpty()) {
          break;
        }

        Checkpoint11Entity composite = byComponentId.get();
        composite.setQuantity(composite.getQuantity() + dto.getQuantity());
        savedComponentQuantities.add(composite);

        checkpoint11Repository.saveAll(savedComponentQuantities);
        result = true;
      }

      case 12 -> {
        List<Checkpoint12Entity> savedComponentQuantities =
          new ArrayList<>();

        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint12Entity> byComponentId =
            checkpoint12Repository.findByComponentId(
              componentsGroupEntity.getComponentId()
            );

          if (byComponentId.isEmpty()) {
            break;
          }

          Checkpoint12Entity checkpointEntity = byComponentId.get();

          double subtractedQuantity =
            checkpointEntity.getQuantity() - componentsGroupEntity.getQuantity();

          if (subtractedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(subtractedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        Optional<Checkpoint12Entity> byComponentId =
          checkpoint12Repository.findByComponentId(compositeId);

        if (byComponentId.isEmpty()) {
          break;
        }

        Checkpoint12Entity composite = byComponentId.get();
        composite.setQuantity(composite.getQuantity() + dto.getQuantity());
        savedComponentQuantities.add(composite);

        checkpoint12Repository.saveAll(savedComponentQuantities);
        result = true;
      }

      case 13 -> {
        List<Checkpoint13Entity> savedComponentQuantities =
          new ArrayList<>();

        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint13Entity> byComponentId =
            checkpoint13Repository.findByComponentId(
              componentsGroupEntity.getComponentId()
            );

          if (byComponentId.isEmpty()) {
            break;
          }

          Checkpoint13Entity checkpointEntity = byComponentId.get();

          double subtractedQuantity =
            checkpointEntity.getQuantity() - componentsGroupEntity.getQuantity();

          if (subtractedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(subtractedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        Optional<Checkpoint13Entity> byComponentId =
          checkpoint13Repository.findByComponentId(compositeId);

        if (byComponentId.isEmpty()) {
          break;
        }

        Checkpoint13Entity composite = byComponentId.get();
        composite.setQuantity(
          composite.getQuantity() + dto.getQuantity()
        );
        savedComponentQuantities.add(composite);

        checkpoint13Repository.saveAll(savedComponentQuantities);
        result = true;
      }

      case 19 -> {
        List<Checkpoint19Entity> savedComponentQuantities =
          new ArrayList<>();

        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint19Entity> byComponentId =
            checkpoint19Repository.findByComponentId(
              componentsGroupEntity.getComponentId()
            );

          if (byComponentId.isEmpty()) {
            break;
          }

          Checkpoint19Entity checkpointEntity = byComponentId.get();

          double subtractedQuantity =
            checkpointEntity.getQuantity() - componentsGroupEntity.getQuantity();

          if (subtractedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(subtractedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        Optional<Checkpoint19Entity> byComponentId =
          checkpoint19Repository.findByComponentId(compositeId);

        if (byComponentId.isEmpty()) {
          break;
        }

        Checkpoint19Entity composite = byComponentId.get();
        composite.setQuantity(
          composite.getQuantity() + dto.getQuantity()
        );
        savedComponentQuantities.add(composite);

        checkpoint19Repository.saveAll(savedComponentQuantities);
        result = true;
      }

      case 20 -> {
        List<Checkpoint20Entity> savedComponentQuantities =
          new ArrayList<>();

        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint20Entity> byComponentId =
            checkpoint20Repository.findByComponentId(
              componentsGroupEntity.getComponentId()
            );

          if (byComponentId.isEmpty()) {
            break;
          }

          Checkpoint20Entity checkpointEntity = byComponentId.get();

          double subtractedQuantity =
            checkpointEntity.getQuantity() - componentsGroupEntity.getQuantity();

          if (subtractedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(subtractedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        Optional<Checkpoint20Entity> byComponentId =
          checkpoint20Repository.findByComponentId(compositeId);

        if (byComponentId.isEmpty()) {
          break;
        }

        Checkpoint20Entity composite = byComponentId.get();
        composite.setQuantity(
          composite.getQuantity() + dto.getQuantity()
        );
        savedComponentQuantities.add(composite);

        checkpoint20Repository.saveAll(savedComponentQuantities);
        result = true;
      }

      case 21 -> {
        List<Checkpoint21Entity> savedComponentQuantities =
          new ArrayList<>();

        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint21Entity> byComponentId =
            checkpoint21Repository.findByComponentId(
              componentsGroupEntity.getComponentId()
            );

          if (byComponentId.isEmpty()) {
            break;
          }

          Checkpoint21Entity checkpointEntity = byComponentId.get();

          double subtractedQuantity =
            checkpointEntity.getQuantity() - componentsGroupEntity.getQuantity();

          if (subtractedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(subtractedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        Optional<Checkpoint21Entity> byComponentId =
          checkpoint21Repository.findByComponentId(compositeId);

        if (byComponentId.isEmpty()) {
          break;
        }

        Checkpoint21Entity composite = byComponentId.get();
        composite.setQuantity(
          composite.getQuantity() + dto.getQuantity()
        );
        savedComponentQuantities.add(composite);

        checkpoint21Repository.saveAll(savedComponentQuantities);
        result = true;
      }

      case 27 -> {
        List<Checkpoint27Entity> savedComponentQuantities =
          new ArrayList<>();

        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint27Entity> byComponentId =
            checkpoint27Repository.findByComponentId(
              componentsGroupEntity.getComponentId()
            );

          if (byComponentId.isEmpty()) {
            break;
          }

          Checkpoint27Entity checkpointEntity = byComponentId.get();

          double subtractedQuantity =
            checkpointEntity.getQuantity() - componentsGroupEntity.getQuantity();

          if (subtractedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(subtractedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        Optional<Checkpoint27Entity> byComponentId =
          checkpoint27Repository.findByComponentId(compositeId);

        if (byComponentId.isEmpty()) {
          break;
        }

        Checkpoint27Entity composite = byComponentId.get();
        composite.setQuantity(
          composite.getQuantity() + dto.getQuantity()
        );
        savedComponentQuantities.add(composite);

        checkpoint27Repository.saveAll(savedComponentQuantities);
        result = true;
      }
    }

    return result;
  }
}
