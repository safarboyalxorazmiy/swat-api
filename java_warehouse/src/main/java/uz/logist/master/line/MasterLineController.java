package uz.logist.master.line;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.jarvis.master.line.dto.LineInfoDTO;
import uz.jarvis.user.User;

import java.util.List;

@RestController
@RequestMapping("/master/line")
@RequiredArgsConstructor
public class MasterLineController {
  private final MasterLineService masterLineService;

  @GetMapping("/put")
  public ResponseEntity<Boolean> put(
      @RequestParam String lineId
  ) {
    Boolean result =
        masterLineService.create(getUserId(), Integer.valueOf(lineId));
    return ResponseEntity.ok(result);
  }

  @GetMapping("/get/all")
  public ResponseEntity<List<LineInfoDTO>> getAll() {
    return ResponseEntity.ok(masterLineService.getLinesInfo());
  }

  @GetMapping("/get")
  public ResponseEntity<LineInfoDTO> getLine() {
    return ResponseEntity.ok(masterLineService.getLineByMasterId(getUserId()));
  }

  private Long getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();

    return user.getId();
  }
}