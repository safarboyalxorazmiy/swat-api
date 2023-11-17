package uz.jarvis.components.group;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jarvis.components.group.dtos.CompositeComponentDeleteDTO;
import uz.jarvis.components.group.dtos.CompositeComponentEditDTO;
import uz.jarvis.components.group.dtos.CompositeComponentInfoDTO;

import java.util.List;

@RestController
@RequestMapping("/components/group")
@RequiredArgsConstructor
public class ComponentGroupController {
  private final ComponentsGroupService componentsGroupService;

  @PostMapping("/create")
  public ResponseEntity<Boolean> create(@RequestBody ComponentsGroupCreateDTO createDTO) {
    return ResponseEntity.ok(componentsGroupService.create(createDTO));
  }

  //  DONE (CompositeId orqali unga ketadigan detallarni olish.)
  @GetMapping("/get/{compositeId}")
  public ResponseEntity<List<CompositeComponentInfoDTO>> getComponents(@PathVariable Long compositeId) {
    return ResponseEntity.ok(componentsGroupService.getComponentsByCompositeId(compositeId));
  }

  //  DONE (CompositeId orqali unga ketadigan detallarni o'zgartirish uchun api yozish.)
  @PutMapping("/edit")
  public ResponseEntity<Boolean> editCompositeComponent(@RequestBody CompositeComponentEditDTO editDTO) {
    return ResponseEntity.ok(componentsGroupService.editCompositeComponent(editDTO));
  }

  // DONE (Composite ichidagi componentni o'chirish uchun api yozish.)
  @DeleteMapping("/delete")
  public ResponseEntity<Boolean> deleteGroup(@RequestBody CompositeComponentDeleteDTO deleteDTO) {
    return ResponseEntity.ok(componentsGroupService.deleteCompositeComponent(deleteDTO));
  }
}