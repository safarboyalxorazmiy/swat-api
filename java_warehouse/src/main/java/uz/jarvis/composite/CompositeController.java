package uz.jarvis.composite;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/composite")
@RequiredArgsConstructor
public class CompositeController {
  private final CompositeService compositeService;

  @PostMapping("/create")
  public ResponseEntity<Boolean> createComposite(
      @RequestBody CompositeCreateDTO dto
  ) {
    return ResponseEntity.ok(
        compositeService.create(dto)
    );
  }
}
