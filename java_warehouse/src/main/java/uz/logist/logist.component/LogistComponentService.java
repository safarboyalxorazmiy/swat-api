package uz.logist.logist.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jarvis.components.ComponentsEntity;
import uz.jarvis.components.ComponentsRepository;
import uz.jarvis.logist.component.dto.LogistComponentInfoDTO;
import uz.jarvis.logist.component.dto.LogistComponentRequestSumbitDTO;
import uz.jarvis.logist.component.dto.LogistInfoDTO;
import uz.jarvis.user.Role;
import uz.jarvis.user.User;
import uz.jarvis.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogistComponentService {
  private final LogistComponentRequestRepository logistComponentRequestRepository;
  private final LogistComponentRepository logistComponentRepository;
  private final ComponentsRepository componentsRepository;
  private final UserRepository userRepository;

  public Boolean submitComponent(LogistComponentRequestSumbitDTO dto) {
    // IS COMPONENT EXISTS??
    Optional<ComponentsEntity> byComponentId = componentsRepository.findById(dto.getComponentId());
    if (byComponentId.isEmpty()) {
      return false;
    }

    // IS LOGIST EXISTS??
    Optional<User> byLogistId = userRepository.findById(dto.getLogistId());
    if (byLogistId.isEmpty()) {
      return false;
    }

    // CREATE REQUEST
    LogistComponentRequestEntity logistComponent = new LogistComponentRequestEntity();
    logistComponent.setLogistId(dto.getLogistId());
    logistComponent.setComponentId(dto.getComponentId());
    logistComponent.setQuantity(dto.getQuantity());
    logistComponent.setVerified(false);

    logistComponentRequestRepository.save(logistComponent);
    return true;
  }

  public List<LogistComponentInfoDTO> getNotVerifiedComponents(Long logistId) {
    List<LogistComponentRequestEntity> byVerifiedFalse = logistComponentRequestRepository.findByVerifiedFalseAndLogistIdOrderByCreatedDateDesc(logistId);

    List<LogistComponentInfoDTO> result = new ArrayList<>();
    for (LogistComponentRequestEntity logistComponent : byVerifiedFalse) {
      LogistComponentInfoDTO dto = new LogistComponentInfoDTO();
      dto.setId(logistComponent.getId());
      dto.setQuantity(logistComponent.getQuantity());
      dto.setComponentId(logistComponent.getComponentId());
      dto.setComponentName(logistComponent.getComponent().getName());
      dto.setComponentCode(logistComponent.getComponent().getCode());
      dto.setComponentSpecs(logistComponent.getComponent().getSpecs());
      dto.setLogistId(logistComponent.getLogistId());
      result.add(dto);
    }

    return result;
  }

  public Boolean verifyRequest(Long requestId) {
    // REQUEST EXISTS??
    Optional<LogistComponentRequestEntity> byRequestId = logistComponentRequestRepository.findById(requestId);
    if (byRequestId.isEmpty()) {
      return false;
    }

    // SET REQUEST VERIFIED
    LogistComponentRequestEntity requestEntity = byRequestId.get();
    requestEntity.setVerified(true);
    logistComponentRequestRepository.save(requestEntity);

    // CHECK COMPONENT EXISTS IN LOGIST
    Optional<LogistComponentEntity> byComponentIdAndLogistId = logistComponentRepository.findByComponentIdAndLogistId(requestEntity.getComponentId(), requestEntity.getLogistId());
    if (byComponentIdAndLogistId.isEmpty()) {
      // NOT EXISTS CREATE ONE
      LogistComponentEntity logistComponent = new LogistComponentEntity();
      logistComponent.setComponentId(requestEntity.getComponentId());
      logistComponent.setLogistId(requestEntity.getLogistId());
      logistComponent.setQuantity(requestEntity.getQuantity());
      logistComponent.setUpdatedDate(LocalDateTime.now());

      logistComponentRepository.save(logistComponent);
      return true;
    }

    // EXISTS SET QUANTITY
    LogistComponentEntity logistComponent = byComponentIdAndLogistId.get();
    logistComponent.setQuantity(logistComponent.getQuantity() + requestEntity.getQuantity());
    logistComponentRepository.save(logistComponent);


    // SET AVAILABLE IN WAREHOUSE
    Optional<ComponentsEntity> byId = componentsRepository.findById(logistComponent.getComponentId());
    if (byId.isEmpty()) {
      return false;
    }

    ComponentsEntity componentsEntity = byId.get();
    componentsEntity.setAvailable(componentsEntity.getAvailable() - requestEntity.getQuantity());
    componentsRepository.save(componentsEntity);
    return true;
  }

  public Boolean cancelRequest(Long requestId) {
    // REQUEST EXISTS??
    Optional<LogistComponentRequestEntity> byRequestId = logistComponentRequestRepository.findById(requestId);
    if (byRequestId.isEmpty()) {
      return false;
    }

    LogistComponentRequestEntity requestEntity = byRequestId.get();

    // SET AVAILABLE IN WAREHOUSE
    Optional<ComponentsEntity> byId = componentsRepository.findById(requestEntity.getComponentId());
    if (byId.isEmpty()) {
      return false;
    }

    logistComponentRequestRepository.delete(requestEntity);
    return true;
  }

  public List<LogistInfoDTO> getAllLogistsInfo() {
    List<User> byRoleLogist = userRepository.findByRole(Role.LOGIST);
    List<LogistInfoDTO> result = new ArrayList<>();
    for (User user : byRoleLogist) {
      LogistInfoDTO dto = new LogistInfoDTO();
      dto.setFirstName(user.getFirstname());
      dto.setLastName(user.getLastname());
      dto.setId(user.getId());
      result.add(dto);
    }

    return result;
  }

  public List<LogistComponentInfoDTO> getComponentsInfoByLogistId(Long logistId) {
    List<LogistComponentEntity> all = logistComponentRepository.findByLogistIdOrderByUpdatedDateDesc(logistId);
    List<LogistComponentInfoDTO> result = new ArrayList<>();
    for (LogistComponentEntity logistComponent : all) {
      LogistComponentInfoDTO dto = new LogistComponentInfoDTO();
      dto.setId(logistComponent.getId());
      dto.setQuantity(logistComponent.getQuantity());
      dto.setComponentId(logistComponent.getComponentId());
      dto.setComponentName(logistComponent.getComponent().getName());
      dto.setComponentCode(logistComponent.getComponent().getCode());
      dto.setComponentSpecs(logistComponent.getComponent().getSpecs());
      dto.setLogistId(logistComponent.getLogistId());
      result.add(dto);
    }
    return result;
  }

  public List<LogistComponentInfoDTO> search(Long logistId, String searchQuery) {
    List<LogistComponentEntity> all = logistComponentRepository.search(searchQuery + "%", logistId);
    List<LogistComponentInfoDTO> result = new ArrayList<>();
    for (LogistComponentEntity logistComponent : all) {
      LogistComponentInfoDTO dto = new LogistComponentInfoDTO();
      dto.setId(logistComponent.getId());
      dto.setQuantity(logistComponent.getQuantity());
      dto.setComponentId(logistComponent.getComponentId());
      dto.setComponentName(logistComponent.getComponent().getName());
      dto.setComponentCode(logistComponent.getComponent().getCode());
      dto.setComponentSpecs(logistComponent.getComponent().getSpecs());
      dto.setLogistId(logistComponent.getLogistId());
      result.add(dto);
    }
    return result;
  }


}