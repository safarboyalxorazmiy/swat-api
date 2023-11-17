package uz.jarvis.components;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/components")
@RequiredArgsConstructor
public class ComponentsController {
  private final ComponentsService componentsService;

  // @DONE Composite detallarni olish uchun api yozish
  @GetMapping("/get/all/composite")
  public ResponseEntity<List<CompositeDTO>> getComposites() {
    return ResponseEntity.ok(componentsService.getComposites());
  }


}