package uz.logist.master.component;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.jarvis.master.component.dto.MasterComponentInfoDTO;
import uz.jarvis.master.component.dto.MasterComponentRequestSumbitDTO;
import uz.jarvis.master.component.dto.MasterInfoDTO;
import uz.jarvis.user.User;

import java.util.List;

@RestController
@RequestMapping("/master")
@RequiredArgsConstructor
public class MasterComponentController {
  private final MasterComponentRequestService masterComponentRequestService;


  @PreAuthorize("permitAll()")
  @PostMapping("/component/submit")
  public ResponseEntity<Boolean> submitComponentRequest(@RequestBody MasterComponentRequestSumbitDTO dto) {
    System.out.println(dto);
    Boolean result = masterComponentRequestService.submitComponent(dto, getUserId());
    return ResponseEntity.ok(result);
  }

  @GetMapping("/get/info")
  public ResponseEntity<List<MasterInfoDTO>> getMasters() {
    List<MasterInfoDTO> result = masterComponentRequestService.getAllMastersInfo();
    return ResponseEntity.ok(result);
  }

  @GetMapping("/component/not/verified")
  public ResponseEntity<List<MasterComponentInfoDTO>> getNotVerifiedRequests() {
    List<MasterComponentInfoDTO> result = masterComponentRequestService.getNotVerifiedComponents(getUserId());
    return ResponseEntity.ok(result);
  }

  @PutMapping("/component/verify")
  public ResponseEntity<Boolean> verifyRequest(@RequestParam Long requestId) {
    Boolean result = masterComponentRequestService.verifyRequest(requestId);
    return ResponseEntity.ok(result);
  }

  @PutMapping("/component/cancel")
  public ResponseEntity<Boolean> cancelRequest(@RequestParam Long requestId) {
    Boolean result = masterComponentRequestService.cancelRequest(requestId);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/component/info")
  public ResponseEntity<List<MasterComponentInfoDTO>> getComponentsInfo() {
    List<MasterComponentInfoDTO> componentsInfo = masterComponentRequestService.getComponentsInfoByLogistId(getUserId());
    return ResponseEntity.ok(componentsInfo);
  }

  private Long getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();

    return user.getId();
  }
}