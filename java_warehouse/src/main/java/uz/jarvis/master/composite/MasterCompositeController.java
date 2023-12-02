package uz.jarvis.master.composite;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jarvis.master.component.dto.MasterCompositeCreateDTO;
import uz.jarvis.user.User;

@RestController
@RequestMapping("/master/composite")
@RequiredArgsConstructor
public class MasterCompositeController {
  private final MasterCompositeService masterCompositeService;

  @PreAuthorize("hasRole('MASTER')")
  @PostMapping("/create")
  public ResponseEntity<Boolean> createComposite(@RequestBody MasterCompositeCreateDTO dto) {
    return ResponseEntity.ok(
      masterCompositeService.createComposite(dto, getUserId())
    );
  }

  private Long getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();

    return user.getId();
  }
}