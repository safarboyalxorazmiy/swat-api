package uz.jarvis.composite;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jarvis.components.ComponentsService;

@RestController
@RequestMapping("/api/v1/composite")
@RequiredArgsConstructor
public class CompositeController {
  private final ComponentsService componentsService;

  @PostMapping("/create")
  public ResponseEntity<Boolean> createComposite(
      @RequestBody CompositeCreateDTO dto
  ) {
    return ResponseEntity.ok(
        componentsService.create(dto)
    );
  }
}
