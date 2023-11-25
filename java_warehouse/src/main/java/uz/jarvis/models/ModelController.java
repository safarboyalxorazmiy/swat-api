package uz.jarvis.models;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/model")
@RequiredArgsConstructor
public class ModelController {
  private final ModelService modelService;

  @PreAuthorize("hasRole('ACCOUNTANT')")
  @GetMapping("/get")
  public ResponseEntity<List<ModelInfoDTO>> getLike(
      @RequestParam String name
  ) {
    return ResponseEntity.ok(modelService.findByNameLike(name));
  }
}