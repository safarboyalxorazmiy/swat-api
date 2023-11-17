package uz.jarvis.master.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jarvis.components.ComponentsEntity;
import uz.jarvis.components.ComponentsRepository;
import uz.jarvis.components.group.ComponentsGroupEntity;
import uz.jarvis.components.group.ComponentsGroupRepository;
import uz.jarvis.components.group.dtos.CompositeComponentInfoDTO;
import uz.jarvis.lines.entity.*;
import uz.jarvis.lines.repository.*;
import uz.jarvis.logist.component.LogistComponentEntity;
import uz.jarvis.logist.component.LogistComponentRepository;
import uz.jarvis.logist.component.dto.LogistComponentInfoDTO;
import uz.jarvis.master.component.dto.MasterComponentInfoDTO;
import uz.jarvis.master.component.dto.MasterComponentRequestSumbitDTO;
import uz.jarvis.master.component.dto.MasterInfoDTO;
import uz.jarvis.master.component.exception.QuantityNotEnoughException;
import uz.jarvis.master.line.MasterLineEntity;
import uz.jarvis.master.line.MasterLineRepository;
import uz.jarvis.user.Role;
import uz.jarvis.user.User;
import uz.jarvis.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MasterComponentRequestService {
  private final MasterComponentRequestRepository masterComponentRequestRepository;
  private final ComponentsRepository componentsRepository;
  private final UserRepository userRepository;
  private final LogistComponentRepository logistComponentRepository;
  private final MasterLineRepository masterLineRepository;

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


  public Boolean submitComponent(MasterComponentRequestSumbitDTO dto, Long logistId) {
    Optional<LogistComponentEntity> byComponentIdAndLogistId = logistComponentRepository.findByComponentIdAndLogistId(dto.getComponentId(), logistId);
    if (byComponentIdAndLogistId.isEmpty()) {
      return false;
    }

    LogistComponentEntity logistComponent = byComponentIdAndLogistId.get();
    double logistQuantity = logistComponent.getQuantity() - dto.getQuantity();
    if (logistQuantity < 0) {
      throw new QuantityNotEnoughException("Miqdor yetarli emas.");
    }

    logistComponent.setQuantity(logistQuantity);
    logistComponent.setUpdatedDate(LocalDateTime.now());
    logistComponentRepository.save(logistComponent);

    // IS COMPONENT EXISTS??
    Optional<ComponentsEntity> byComponentId = componentsRepository.findById(dto.getComponentId());
    if (byComponentId.isEmpty()) {
      return false;
    }

    // IS MASTER EXISTS??
    Optional<User> byLogistId = userRepository.findById(dto.getMasterId());
    if (byLogistId.isEmpty()) {
      return false;
    }

    createRequest(dto.getMasterId(), logistId, FROM.LOGIST, dto.getComponentId(), dto.getQuantity());
    return true;
  }

  public Boolean submitComponentFromMaster(MasterComponentRequestSumbitDTO dto, Long fromMasterId) {
    Optional<MasterLineEntity> byMasterId =
      masterLineRepository.findByMasterId(fromMasterId);
    if (byMasterId.isEmpty()) {
      return false;
    }

    Long compositeId = dto.getComponentId();
    List<ComponentsGroupEntity> byCompositeId = componentsGroupRepository.findByCompositeId(compositeId);

    MasterLineEntity masterLineEntity = byMasterId.get();
    Integer lineId = masterLineEntity.getLineId();
    switch (lineId) {
      case 1 -> {
        List<Checkpoint1Entity> savedComponentQuantities = new ArrayList<>();
        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint1Entity> byComponentId = checkpoint1Repository.findByComponentId(componentsGroupEntity.getComponentId());
          if (byComponentId.isEmpty()) {
            return false;
          }

          Checkpoint1Entity checkpointEntity = byComponentId.get();
          double minusedQuantity = checkpointEntity.getQuantity() - dto.getQuantity();
          if (minusedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }
          checkpointEntity.setQuantity(minusedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        for (Checkpoint1Entity savedComponentQuantity : savedComponentQuantities) {
          checkpoint1Repository.save(savedComponentQuantity);
        }

        createRequest(dto.getMasterId(), fromMasterId, FROM.MASTER, dto.getComponentId(), dto.getQuantity());
        return true;
      }

      case 2 -> {
        List<Checkpoint2Entity> savedComponentQuantities = new ArrayList<>();
        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint2Entity> byComponentId = checkpoint2Repository.findByComponentId(componentsGroupEntity.getComponentId());
          if (byComponentId.isEmpty()) {
            return false;
          }

          Checkpoint2Entity checkpointEntity = byComponentId.get();
          double minusedQuantity = checkpointEntity.getQuantity() - (componentsGroupEntity.getQuantity() * dto.getQuantity());
          if (minusedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }
          checkpointEntity.setQuantity(minusedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        for (Checkpoint2Entity savedComponentQuantity : savedComponentQuantities) {
          checkpoint2Repository.save(savedComponentQuantity);
        }

        createRequest(dto.getMasterId(), fromMasterId, FROM.MASTER, dto.getComponentId(), dto.getQuantity());
        return true;
      }

      case 9 -> {
        List<Checkpoint9Entity> savedComponentQuantities = new ArrayList<>();
        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint9Entity> byComponentId = checkpoint9Repository.findByComponentId(componentsGroupEntity.getComponentId());
          if (byComponentId.isEmpty()) {
            return false;
          }

          Checkpoint9Entity checkpointEntity = byComponentId.get();
          double minusedQuantity = checkpointEntity.getQuantity() - dto.getQuantity();
          if (minusedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(minusedQuantity);

          savedComponentQuantities.add(checkpointEntity);
        }

        for (Checkpoint9Entity savedComponentQuantity : savedComponentQuantities) {
          checkpoint9Repository.save(savedComponentQuantity);
        }

        createRequest(dto.getMasterId(), fromMasterId, FROM.MASTER, dto.getComponentId(), dto.getQuantity());
        return true;
      }
      case 10 -> {
        List<Checkpoint10Entity> savedComponentQuantities = new ArrayList<>();
        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint10Entity> byComponentId = checkpoint10Repository.findByComponentId(componentsGroupEntity.getComponentId());
          if (byComponentId.isEmpty()) {
            return false;
          }

          Checkpoint10Entity checkpointEntity = byComponentId.get();
          double minusedQuantity = checkpointEntity.getQuantity() - dto.getQuantity();
          if (minusedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(minusedQuantity);
          savedComponentQuantities.add(checkpointEntity);
        }

        for (Checkpoint10Entity savedComponentQuantity : savedComponentQuantities) {
          checkpoint10Repository.save(savedComponentQuantity);
        }

        return true;
      }

      case 11 -> {
        List<Checkpoint11Entity> savedComponentQuantities = new ArrayList<>();
        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint11Entity> byComponentId = checkpoint11Repository.findByComponentId(componentsGroupEntity.getComponentId());
          if (byComponentId.isEmpty()) {
            return false;
          }

          Checkpoint11Entity checkpointEntity = byComponentId.get();
          double minusedQuantity = checkpointEntity.getQuantity() - dto.getQuantity();
          if (minusedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(minusedQuantity);
          savedComponentQuantities.add(checkpointEntity);
        }

        for (Checkpoint11Entity savedComponentQuantity : savedComponentQuantities) {
          checkpoint11Repository.save(savedComponentQuantity);
        }

        createRequest(dto.getMasterId(), fromMasterId, FROM.MASTER, dto.getComponentId(), dto.getQuantity());
        return true;
      }

      case 12 -> {
        List<Checkpoint12Entity> savedComponentQuantities = new ArrayList<>();
        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint12Entity> byComponentId = checkpoint12Repository.findByComponentId(componentsGroupEntity.getComponentId());
          if (byComponentId.isEmpty()) {
            return false;
          }

          Checkpoint12Entity checkpointEntity = byComponentId.get();
          double minusedQuantity = checkpointEntity.getQuantity() - dto.getQuantity();
          if (minusedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(minusedQuantity);
          savedComponentQuantities.add(checkpointEntity);
        }

        for (Checkpoint12Entity savedComponentQuantity : savedComponentQuantities) {
          checkpoint12Repository.save(savedComponentQuantity);
        }

        createRequest(dto.getMasterId(), fromMasterId, FROM.MASTER, dto.getComponentId(), dto.getQuantity());
        return true;
      }

      case 13 -> {
        List<Checkpoint13Entity> savedComponentQuantities = new ArrayList<>();
        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint13Entity> byComponentId = checkpoint13Repository.findByComponentId(componentsGroupEntity.getComponentId());
          if (byComponentId.isEmpty()) {
            return false;
          }

          Checkpoint13Entity checkpointEntity = byComponentId.get();
          double minusedQuantity = checkpointEntity.getQuantity() - dto.getQuantity();
          if (minusedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(minusedQuantity);
          savedComponentQuantities.add(checkpointEntity);
        }

        for (Checkpoint13Entity savedComponentQuantity : savedComponentQuantities) {
          checkpoint13Repository.save(savedComponentQuantity);
        }

        createRequest(dto.getMasterId(), fromMasterId, FROM.MASTER, dto.getComponentId(), dto.getQuantity());
        return true;
      }
      case 19 -> {
        List<Checkpoint19Entity> savedComponentQuantities = new ArrayList<>();
        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint19Entity> byComponentId = checkpoint19Repository.findByComponentId(componentsGroupEntity.getComponentId());
          if (byComponentId.isEmpty()) {
            return false;
          }

          Checkpoint19Entity checkpointEntity = byComponentId.get();
          double minusedQuantity = checkpointEntity.getQuantity() - dto.getQuantity();
          if (minusedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpointEntity.setQuantity(minusedQuantity);
          savedComponentQuantities.add(checkpointEntity);
        }

        for (Checkpoint19Entity savedComponentQuantity : savedComponentQuantities) {
          checkpoint19Repository.save(savedComponentQuantity);
        }

        createRequest(dto.getMasterId(), fromMasterId, FROM.MASTER, dto.getComponentId(), dto.getQuantity());
        return true;
      }

      case 20 -> {
        List<Checkpoint20Entity> savedComponentQuantities = new ArrayList<>();
        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint20Entity> byComponentId = checkpoint20Repository.findByComponentId(componentsGroupEntity.getComponentId());
          if (byComponentId.isEmpty()) {
            return false;
          }

          Checkpoint20Entity checkpoint20Entity = byComponentId.get();
          double minusedQuantity = checkpoint20Entity.getQuantity() - dto.getQuantity();
          if (minusedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpoint20Entity.setQuantity(minusedQuantity);
          savedComponentQuantities.add(checkpoint20Entity);
        }

        for (Checkpoint20Entity savedComponentQuantity : savedComponentQuantities) {
          checkpoint20Repository.save(savedComponentQuantity);
        }

        createRequest(dto.getMasterId(), fromMasterId, FROM.MASTER, dto.getComponentId(), dto.getQuantity());
        return true;
      }

      case 21 -> {
        List<Checkpoint21Entity> savedComponentQuantities = new ArrayList<>();
        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint21Entity> byComponentId = checkpoint21Repository.findByComponentId(componentsGroupEntity.getComponentId());
          if (byComponentId.isEmpty()) {
            return false;
          }

          Checkpoint21Entity checkpoint21Entity = byComponentId.get();
          double minusedQuantity = checkpoint21Entity.getQuantity() - dto.getQuantity();
          if (minusedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpoint21Entity.setQuantity(minusedQuantity);
          savedComponentQuantities.add(checkpoint21Entity);
        }

        for (Checkpoint21Entity savedComponentQuantity : savedComponentQuantities) {
          checkpoint21Repository.save(savedComponentQuantity);
        }

        createRequest(dto.getMasterId(), fromMasterId, FROM.MASTER, dto.getComponentId(), dto.getQuantity());
        return true;
      }
      case 27 -> {
        List<Checkpoint27Entity> savedComponentQuantities = new ArrayList<>();
        for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
          Optional<Checkpoint27Entity> byComponentId = checkpoint27Repository.findByComponentId(componentsGroupEntity.getComponentId());
          if (byComponentId.isEmpty()) {
            return false;
          }

          Checkpoint27Entity checkpoint27Entity = byComponentId.get();
          double minusedQuantity = checkpoint27Entity.getQuantity() - dto.getQuantity();
          if (minusedQuantity < 0) {
            throw new QuantityNotEnoughException("Miqdor yetarli emas.");
          }

          checkpoint27Entity.setQuantity(minusedQuantity);
          savedComponentQuantities.add(checkpoint27Entity);
        }

        for (Checkpoint27Entity savedComponentQuantity : savedComponentQuantities) {
          checkpoint27Repository.save(savedComponentQuantity);
        }

        createRequest(dto.getMasterId(), fromMasterId, FROM.MASTER, dto.getComponentId(), dto.getQuantity());
        return true;
      }
    }


    return false;
  }

  public List<MasterInfoDTO> getAllMastersInfo() {
    List<User> byRoleLogist = userRepository.findByRole(Role.MASTER);
    List<MasterInfoDTO> result = new ArrayList<>();
    for (User user : byRoleLogist) {
      MasterInfoDTO dto = new MasterInfoDTO();
      dto.setFirstName(user.getFirstname());
      dto.setLastName(user.getLastname());
      dto.setId(user.getId());
      result.add(dto);
    }

    return result;
  }

  public List<MasterComponentInfoDTO> getNotVerifiedComponents(Long masterId) {
    List<MasterComponentRequestEntity> byVerifiedFalse = masterComponentRequestRepository.findByVerifiedFalseAndMasterIdOrderByCreatedDateDesc(masterId);

    List<MasterComponentInfoDTO> result = new ArrayList<>();
    for (MasterComponentRequestEntity logistComponent : byVerifiedFalse) {
      MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
      dto.setId(logistComponent.getId());
      dto.setQuantity(logistComponent.getQuantity());
      dto.setComponentId(logistComponent.getComponentId());
      dto.setComponentName(logistComponent.getComponent().getName());
      dto.setComponentCode(logistComponent.getComponent().getCode());
      dto.setComponentSpecs(logistComponent.getComponent().getSpecs());
      dto.setMasterId(logistComponent.getMasterId());
      result.add(dto);
    }

    return result;
  }

  public Boolean verifyRequest(Long requestId) {
    Optional<MasterComponentRequestEntity> byId = masterComponentRequestRepository.findById(requestId);
    if (byId.isEmpty()) {
      return false;
    }
    MasterComponentRequestEntity componentRequest = byId.get();
    Optional<MasterLineEntity> byMasterId =
      masterLineRepository.findByMasterId(componentRequest.getMasterId());
    if (byMasterId.isEmpty()) {
      return false;
    }

    MasterLineEntity masterLineEntity = byMasterId.get();
    Integer lineId = masterLineEntity.getLineId();
    Long componentId = componentRequest.getComponentId();
    boolean added = false;

    switch (lineId) {
      case 1 -> {
        Optional<Checkpoint1Entity> byComponentId =
          checkpoint1Repository.findByComponentId(componentId);
        if (byComponentId.isEmpty()) {
          return false;
        }

        Checkpoint1Entity currentCheckpoint = byComponentId.get();
        currentCheckpoint.setQuantity(
          currentCheckpoint.getQuantity() + componentRequest.getQuantity()
        );
        checkpoint1Repository.save(currentCheckpoint);
        added = true;
        break;
      }
      case 2 -> {
        Optional<Checkpoint2Entity> byComponentId =
          checkpoint2Repository.findByComponentId(componentId);
        if (byComponentId.isEmpty()) {
          return false;
        }

        Checkpoint2Entity currentCheckpoint = byComponentId.get();
        currentCheckpoint.setQuantity(
          currentCheckpoint.getQuantity() + componentRequest.getQuantity()
        );
        checkpoint2Repository.save(currentCheckpoint);
        added = true;
        break;
      }
      case 9 -> {
        Optional<Checkpoint9Entity> byComponentId =
          checkpoint9Repository.findByComponentId(componentId);
        if (byComponentId.isEmpty()) {
          return false;
        }

        Checkpoint9Entity currentCheckpoint = byComponentId.get();
        currentCheckpoint.setQuantity(
          currentCheckpoint.getQuantity() + componentRequest.getQuantity()
        );
        checkpoint9Repository.save(currentCheckpoint);
        added = true;
        break;
      }
      case 10 -> {
        Optional<Checkpoint10Entity> byComponentId =
          checkpoint10Repository.findByComponentId(componentId);
        if (byComponentId.isEmpty()) {
          return false;
        }

        Checkpoint10Entity currentCheckpoint = byComponentId.get();
        currentCheckpoint.setQuantity(
          currentCheckpoint.getQuantity() + componentRequest.getQuantity()
        );
        checkpoint10Repository.save(currentCheckpoint);
        added = true;
        break;
      }
      case 11 -> {
        Optional<Checkpoint11Entity> byComponentId =
          checkpoint11Repository.findByComponentId(componentId);
        if (byComponentId.isEmpty()) {
          return false;
        }

        Checkpoint11Entity currentCheckpoint = byComponentId.get();
        currentCheckpoint.setQuantity(
          currentCheckpoint.getQuantity() + componentRequest.getQuantity()
        );
        checkpoint11Repository.save(currentCheckpoint);
        added = true;
        break;
      }
      case 12 -> {
        Optional<Checkpoint12Entity> byComponentId =
          checkpoint12Repository.findByComponentId(componentId);
        if (byComponentId.isEmpty()) {
          return false;
        }

        Checkpoint12Entity currentCheckpoint = byComponentId.get();
        currentCheckpoint.setQuantity(
          currentCheckpoint.getQuantity() + componentRequest.getQuantity()
        );
        checkpoint12Repository.save(currentCheckpoint);
        added = true;
        break;
      }
      case 13 -> {
        Optional<Checkpoint13Entity> byComponentId =
          checkpoint13Repository.findByComponentId(componentId);
        if (byComponentId.isEmpty()) {
          return false;
        }

        Checkpoint13Entity currentCheckpoint = byComponentId.get();
        currentCheckpoint.setQuantity(
          currentCheckpoint.getQuantity() + componentRequest.getQuantity()
        );
        checkpoint13Repository.save(currentCheckpoint);
        added = true;
        break;
      }
      case 19 -> {
        Optional<Checkpoint19Entity> byComponentId =
          checkpoint19Repository.findByComponentId(componentId);
        if (byComponentId.isEmpty()) {
          return false;
        }

        Checkpoint19Entity currentCheckpoint = byComponentId.get();
        currentCheckpoint.setQuantity(
          currentCheckpoint.getQuantity() + componentRequest.getQuantity()
        );
        checkpoint19Repository.save(currentCheckpoint);
        added = true;
        break;
      }
      case 20 -> {
        Optional<Checkpoint20Entity> byComponentId =
          checkpoint20Repository.findByComponentId(componentId);
        if (byComponentId.isEmpty()) {
          return false;
        }

        Checkpoint20Entity currentCheckpoint = byComponentId.get();
        currentCheckpoint.setQuantity(
          currentCheckpoint.getQuantity() + componentRequest.getQuantity()
        );
        checkpoint20Repository.save(currentCheckpoint);
        added = true;
        break;
      }
      case 21 -> {
        Optional<Checkpoint21Entity> byComponentId =
          checkpoint21Repository.findByComponentId(componentId);
        if (byComponentId.isEmpty()) {
          return false;
        }

        Checkpoint21Entity currentCheckpoint = byComponentId.get();
        currentCheckpoint.setQuantity(
          currentCheckpoint.getQuantity() + componentRequest.getQuantity()
        );
        checkpoint21Repository.save(currentCheckpoint);
        added = true;
        break;
      }
      case 27 -> {
        Optional<Checkpoint27Entity> byComponentId =
          checkpoint27Repository.findByComponentId(componentId);
        if (byComponentId.isEmpty()) {
          return false;
        }

        Checkpoint27Entity currentCheckpoint = byComponentId.get();
        currentCheckpoint.setQuantity(
          currentCheckpoint.getQuantity() + componentRequest.getQuantity()
        );
        checkpoint27Repository.save(currentCheckpoint);
        added = true;
        break;
      }
    }

    if (added) {
      componentRequest.setVerified(true);
      masterComponentRequestRepository.save(componentRequest);
      return true;
    }

    return false;
  }

  public Boolean cancelRequest(Long requestId) {
    // REQUEST EXISTS??
    Optional<MasterComponentRequestEntity> byRequestId = masterComponentRequestRepository.findById(requestId);
    if (byRequestId.isEmpty()) {
      return false;
    }

    // DELETE REQUEST
    MasterComponentRequestEntity requestEntity = byRequestId.get();
    if (requestEntity.getFromType().equals(FROM.LOGIST)) {
      // SET AVAILABLE IN WAREHOUSE
      Optional<LogistComponentEntity> byId = logistComponentRepository.findByComponentIdAndLogistId(
        requestEntity.getComponentId(), requestEntity.getFromId()
      );
      if (byId.isEmpty()) {
        return false;
      }

      LogistComponentEntity componentsEntity = byId.get();
      componentsEntity.setQuantity(componentsEntity.getQuantity() + requestEntity.getQuantity());
      logistComponentRepository.save(componentsEntity);

      masterComponentRequestRepository.delete(requestEntity);
      return true;
    }

    if (requestEntity.getFromType().equals(FROM.MASTER)) {
      Long fromMasterId = requestEntity.getFromId();
      Optional<MasterLineEntity> byMasterId =
        masterLineRepository.findByMasterId(fromMasterId);
      if (byMasterId.isEmpty()) {
        return false;
      }

      Long compositeId = requestEntity.getComponentId();
      List<ComponentsGroupEntity> byCompositeId = componentsGroupRepository.findByCompositeId(compositeId);

      MasterLineEntity masterLineEntity = byMasterId.get();
      Integer lineId = masterLineEntity.getLineId();
      switch (lineId) {
        case 1 -> {
          List<Checkpoint1Entity> savedComponentQuantities = new ArrayList<>();
          for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
            Optional<Checkpoint1Entity> byComponentId = checkpoint1Repository.findByComponentId(componentsGroupEntity.getComponentId());
            if (byComponentId.isEmpty()) {
              return false;
            }

            Checkpoint1Entity checkpointEntity = byComponentId.get();
            double addedQuantity = checkpointEntity.getQuantity() + requestEntity.getQuantity();
            checkpointEntity.setQuantity(addedQuantity);

            savedComponentQuantities.add(checkpointEntity);
          }

          for (Checkpoint1Entity savedComponentQuantity : savedComponentQuantities) {
            checkpoint1Repository.save(savedComponentQuantity);
          }
          return true;
        }

        case 2 -> {
          List<Checkpoint2Entity> savedComponentQuantities = new ArrayList<>();
          for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
            Optional<Checkpoint2Entity> byComponentId = checkpoint2Repository.findByComponentId(componentsGroupEntity.getComponentId());
            if (byComponentId.isEmpty()) {
              return false;
            }

            Checkpoint2Entity checkpointEntity = byComponentId.get();
            double addedQuantity = checkpointEntity.getQuantity() + (componentsGroupEntity.getQuantity() * requestEntity.getQuantity());
            checkpointEntity.setQuantity(addedQuantity);

            savedComponentQuantities.add(checkpointEntity);
          }

          for (Checkpoint2Entity savedComponentQuantity : savedComponentQuantities) {
            checkpoint2Repository.save(savedComponentQuantity);
          }
          return true;
        }

        case 9 -> {
          List<Checkpoint9Entity> savedComponentQuantities = new ArrayList<>();
          for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
            Optional<Checkpoint9Entity> byComponentId = checkpoint9Repository.findByComponentId(componentsGroupEntity.getComponentId());
            if (byComponentId.isEmpty()) {
              return false;
            }

            Checkpoint9Entity checkpointEntity = byComponentId.get();
            double addedQuantity = checkpointEntity.getQuantity() + requestEntity.getQuantity();
            checkpointEntity.setQuantity(addedQuantity);

            savedComponentQuantities.add(checkpointEntity);
          }

          for (Checkpoint9Entity savedComponentQuantity : savedComponentQuantities) {
            checkpoint9Repository.save(savedComponentQuantity);
          }

          return true;
        }
        case 10 -> {
          List<Checkpoint10Entity> savedComponentQuantities = new ArrayList<>();
          for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
            Optional<Checkpoint10Entity> byComponentId = checkpoint10Repository.findByComponentId(componentsGroupEntity.getComponentId());
            if (byComponentId.isEmpty()) {
              return false;
            }

            Checkpoint10Entity checkpointEntity = byComponentId.get();
            double addedQuantity = checkpointEntity.getQuantity() + requestEntity.getQuantity();
            checkpointEntity.setQuantity(addedQuantity);

            savedComponentQuantities.add(checkpointEntity);
          }

          for (Checkpoint10Entity savedComponentQuantity : savedComponentQuantities) {
            checkpoint10Repository.save(savedComponentQuantity);
          }

          return true;
        }

        case 11 -> {
          List<Checkpoint11Entity> savedComponentQuantities = new ArrayList<>();
          for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
            Optional<Checkpoint11Entity> byComponentId = checkpoint11Repository.findByComponentId(componentsGroupEntity.getComponentId());
            if (byComponentId.isEmpty()) {
              return false;
            }

            Checkpoint11Entity checkpointEntity = byComponentId.get();
            double addedQuantity = checkpointEntity.getQuantity() + requestEntity.getQuantity();
            checkpointEntity.setQuantity(addedQuantity);
            savedComponentQuantities.add(checkpointEntity);
          }

          for (Checkpoint11Entity savedComponentQuantity : savedComponentQuantities) {
            checkpoint11Repository.save(savedComponentQuantity);
          }

          return true;
        }

        case 12 -> {
          List<Checkpoint12Entity> savedComponentQuantities = new ArrayList<>();
          for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
            Optional<Checkpoint12Entity> byComponentId = checkpoint12Repository.findByComponentId(componentsGroupEntity.getComponentId());
            if (byComponentId.isEmpty()) {
              return false;
            }

            Checkpoint12Entity checkpointEntity = byComponentId.get();
            double addedQuantity = checkpointEntity.getQuantity() + requestEntity.getQuantity();
            checkpointEntity.setQuantity(addedQuantity);
            savedComponentQuantities.add(checkpointEntity);
          }

          for (Checkpoint12Entity savedComponentQuantity : savedComponentQuantities) {
            checkpoint12Repository.save(savedComponentQuantity);
          }

          return true;
        }

        case 13 -> {
          List<Checkpoint13Entity> savedComponentQuantities = new ArrayList<>();
          for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
            Optional<Checkpoint13Entity> byComponentId = checkpoint13Repository.findByComponentId(componentsGroupEntity.getComponentId());
            if (byComponentId.isEmpty()) {
              return false;
            }

            Checkpoint13Entity checkpointEntity = byComponentId.get();
            double addedQuantity = checkpointEntity.getQuantity() + requestEntity.getQuantity();
            checkpointEntity.setQuantity(addedQuantity);
            savedComponentQuantities.add(checkpointEntity);
          }

          for (Checkpoint13Entity savedComponentQuantity : savedComponentQuantities) {
            checkpoint13Repository.save(savedComponentQuantity);
          }

          return true;
        }
        case 19 -> {
          List<Checkpoint19Entity> savedComponentQuantities = new ArrayList<>();
          for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
            Optional<Checkpoint19Entity> byComponentId = checkpoint19Repository.findByComponentId(componentsGroupEntity.getComponentId());
            if (byComponentId.isEmpty()) {
              return false;
            }

            Checkpoint19Entity checkpointEntity = byComponentId.get();
            double addedQuantity = checkpointEntity.getQuantity() + requestEntity.getQuantity();
            checkpointEntity.setQuantity(addedQuantity);
            savedComponentQuantities.add(checkpointEntity);
          }

          for (Checkpoint19Entity savedComponentQuantity : savedComponentQuantities) {
            checkpoint19Repository.save(savedComponentQuantity);
          }
          return true;
        }

        case 20 -> {
          List<Checkpoint20Entity> savedComponentQuantities = new ArrayList<>();
          for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
            Optional<Checkpoint20Entity> byComponentId = checkpoint20Repository.findByComponentId(componentsGroupEntity.getComponentId());
            if (byComponentId.isEmpty()) {
              return false;
            }

            Checkpoint20Entity checkpoint20Entity = byComponentId.get();
            double addedQuantity = checkpoint20Entity.getQuantity() + requestEntity.getQuantity();
            checkpoint20Entity.setQuantity(addedQuantity);
            savedComponentQuantities.add(checkpoint20Entity);
          }

          for (Checkpoint20Entity savedComponentQuantity : savedComponentQuantities) {
            checkpoint20Repository.save(savedComponentQuantity);
          }

          return true;
        }

        case 21 -> {
          List<Checkpoint21Entity> savedComponentQuantities = new ArrayList<>();
          for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
            Optional<Checkpoint21Entity> byComponentId = checkpoint21Repository.findByComponentId(componentsGroupEntity.getComponentId());
            if (byComponentId.isEmpty()) {
              return false;
            }

            Checkpoint21Entity checkpoint21Entity = byComponentId.get();
            double addedQuantity = checkpoint21Entity.getQuantity() + requestEntity.getQuantity();
            checkpoint21Entity.setQuantity(addedQuantity);
            savedComponentQuantities.add(checkpoint21Entity);
          }

          for (Checkpoint21Entity savedComponentQuantity : savedComponentQuantities) {
            checkpoint21Repository.save(savedComponentQuantity);
          }

          return true;
        }
        case 27 -> {
          List<Checkpoint27Entity> savedComponentQuantities = new ArrayList<>();
          for (ComponentsGroupEntity componentsGroupEntity : byCompositeId) {
            Optional<Checkpoint27Entity> byComponentId = checkpoint27Repository.findByComponentId(componentsGroupEntity.getComponentId());
            if (byComponentId.isEmpty()) {
              return false;
            }

            Checkpoint27Entity checkpoint27Entity = byComponentId.get();
            double addedQuantity = checkpoint27Entity.getQuantity() + requestEntity.getQuantity();
            checkpoint27Entity.setQuantity(addedQuantity);
            savedComponentQuantities.add(checkpoint27Entity);
          }

          for (Checkpoint27Entity savedComponentQuantity : savedComponentQuantities) {
            checkpoint27Repository.save(savedComponentQuantity);
          }

          return true;
        }
      }
      return true;
    }

    return false;
  }

  public List<MasterComponentInfoDTO> getComponentsInfoByMasterId(Long masterId) {
    Optional<MasterLineEntity> byMasterId =
      masterLineRepository.findByMasterId(masterId);
    if (byMasterId.isEmpty()) {
      return null;
    }

    MasterLineEntity masterLineEntity = byMasterId.get();
    Integer lineId = masterLineEntity.getLineId();
    switch (lineId) {
      case 1 -> {
        List<Checkpoint1Entity> all =
          checkpoint1Repository.findByComponentIsMultipleFalse();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint1Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 2 -> {
        List<Checkpoint2Entity> all =
          checkpoint2Repository.findByComponentIsMultipleFalse();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint2Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 9 -> {
        List<Checkpoint9Entity> all =
          checkpoint9Repository.findByComponentIsMultipleFalse();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint9Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 10 -> {
        List<Checkpoint10Entity> all =
          checkpoint10Repository.findByComponentIsMultipleFalse();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint10Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 11 -> {
        List<Checkpoint11Entity> all =
          checkpoint11Repository.findByComponentIsMultipleFalse();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint11Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 12 -> {
        List<Checkpoint12Entity> all =
          checkpoint12Repository.findByComponentIsMultipleFalse();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint12Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 13 -> {
        List<Checkpoint13Entity> all =
          checkpoint13Repository.findByComponentIsMultipleFalse();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint13Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 19 -> {
        List<Checkpoint19Entity> all =
          checkpoint19Repository.findByComponentIsMultipleFalse();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint19Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 20 -> {
        List<Checkpoint20Entity> all =
          checkpoint20Repository.findByComponentIsMultipleFalse();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint20Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 21 -> {
        List<Checkpoint21Entity> all =
          checkpoint21Repository.findByComponentIsMultipleFalse();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint21Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 27 -> {
        List<Checkpoint27Entity> all =
          checkpoint27Repository.findByComponentIsMultipleFalse();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint27Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
    }

    return null;
  }

  public List<MasterComponentInfoDTO> getCompositesInfoByMasterId(Long masterId) {
    Optional<MasterLineEntity> byMasterId =
      masterLineRepository.findByMasterId(masterId);
    if (byMasterId.isEmpty()) {
      return null;
    }

    MasterLineEntity masterLineEntity = byMasterId.get();
    Integer lineId = masterLineEntity.getLineId();
    switch (lineId) {
      case 1 -> {
        List<Checkpoint1Entity> all =
          checkpoint1Repository.findByComponentIsMultipleTrue();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint1Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 2 -> {
        List<Checkpoint2Entity> all =
          checkpoint2Repository.findByComponentIsMultipleTrue();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint2Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 9 -> {
        List<Checkpoint9Entity> all =
          checkpoint9Repository.findByComponentIsMultipleTrue();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint9Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 10 -> {
        List<Checkpoint10Entity> all =
          checkpoint10Repository.findByComponentIsMultipleTrue();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint10Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 11 -> {
        List<Checkpoint11Entity> all =
          checkpoint11Repository.findByComponentIsMultipleTrue();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint11Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 12 -> {
        List<Checkpoint12Entity> all =
          checkpoint12Repository.findByComponentIsMultipleTrue();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint12Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 13 -> {
        List<Checkpoint13Entity> all =
          checkpoint13Repository.findByComponentIsMultipleTrue();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint13Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 19 -> {
        List<Checkpoint19Entity> all =
          checkpoint19Repository.findByComponentIsMultipleTrue();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint19Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 20 -> {
        List<Checkpoint20Entity> all =
          checkpoint20Repository.findByComponentIsMultipleTrue();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint20Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 21 -> {
        List<Checkpoint21Entity> all =
          checkpoint21Repository.findByComponentIsMultipleTrue();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint21Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 27 -> {
        List<Checkpoint27Entity> all =
          checkpoint27Repository.findByComponentIsMultipleTrue();
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint27Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
    }

    return null;
  }

  public List<MasterComponentInfoDTO> search(Long masterId, String searchQuery) {

    searchQuery = searchQuery + "%";

    Optional<MasterLineEntity> byMasterId =
      masterLineRepository.findByMasterId(masterId);
    if (byMasterId.isEmpty()) {
      return null;
    }

    MasterLineEntity masterLineEntity = byMasterId.get();
    Integer lineId = masterLineEntity.getLineId();

    switch (lineId) {
      case 1 -> {
        List<Checkpoint1Entity> all =
          checkpoint1Repository.search(searchQuery);
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint1Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 2 -> {
        List<Checkpoint2Entity> all =
          checkpoint2Repository.search(searchQuery);
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint2Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 9 -> {
        List<Checkpoint9Entity> all =
          checkpoint9Repository.search(searchQuery);
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint9Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 10 -> {
        List<Checkpoint10Entity> all =
          checkpoint10Repository.search(searchQuery);
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint10Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 11 -> {
        List<Checkpoint11Entity> all =
          checkpoint11Repository.search(searchQuery);
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint11Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 12 -> {
        List<Checkpoint12Entity> all =
          checkpoint12Repository.search(searchQuery);
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint12Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 13 -> {
        List<Checkpoint13Entity> all =
          checkpoint13Repository.search(searchQuery);
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint13Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 19 -> {
        List<Checkpoint19Entity> all =
          checkpoint19Repository.search(searchQuery);
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint19Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 20 -> {
        List<Checkpoint20Entity> all =
          checkpoint20Repository.search(searchQuery);
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint20Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 21 -> {
        List<Checkpoint21Entity> all =
          checkpoint21Repository.search(searchQuery);
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint21Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
      case 27 -> {
        List<Checkpoint27Entity> all =
          checkpoint27Repository.search(searchQuery);
        List<MasterComponentInfoDTO> result = new ArrayList<>();
        for (Checkpoint27Entity checkpoint : all) {
          MasterComponentInfoDTO dto = new MasterComponentInfoDTO();
          dto.setId(checkpoint.getId());

          dto.setComponentId(checkpoint.getComponentId());
          dto.setComponentCode(checkpoint.getComponent().getCode());
          dto.setComponentName(checkpoint.getComponent().getName());
          dto.setComponentSpecs(checkpoint.getComponent().getSpecs());

          dto.setQuantity(checkpoint.getQuantity());
          dto.setMasterId(masterId);
          result.add(dto);
        }

        return result;
      }
    }

    return null;
  }


  private void createRequest(Long masterId, Long fromId, FROM fromType, Long componentId, Double quantity) {
    MasterComponentRequestEntity componentRequest = new MasterComponentRequestEntity();
    componentRequest.setMasterId(masterId);
    componentRequest.setFromId(fromId);
    componentRequest.setFromType(fromType);
    componentRequest.setComponentId(componentId);
    componentRequest.setQuantity(quantity);
    componentRequest.setVerified(false);

    masterComponentRequestRepository.save(componentRequest);
  }
}