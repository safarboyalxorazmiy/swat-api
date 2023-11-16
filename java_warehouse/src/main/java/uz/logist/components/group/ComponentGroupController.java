package uz.logist.components.group;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/components/group")
@RequiredArgsConstructor
public class ComponentGroupController {
  private final ComponentsGroupService componentsGroupService;

  @PreAuthorize("permitAll()")
  @PostMapping("/create")
  private ResponseEntity<Boolean> create(@RequestBody ComponentsGroupCreateDTO createDTO) {
    return ResponseEntity.ok(componentsGroupService.create(createDTO));
  }

  // Detallarni guruhlash tamom endi
}