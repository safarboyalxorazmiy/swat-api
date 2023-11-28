package uz.jarvis.master.component.checkpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jarvis.user.User;

@RestController
@RequestMapping("/api/v1/checkpoint")
@RequiredArgsConstructor
public class CheckpointController {
  private final CheckpointService checkpointService;

  @PreAuthorize("hasRole('MASTER')")
  @GetMapping("/get/line/info")
  public ResponseEntity<CheckpointInfoDTO> getLineInfo() {
    return ResponseEntity.ok(checkpointService.getLineInfo(
      getUserId()
    ));
  }

  private Long getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();

    return user.getId();
  }
}
