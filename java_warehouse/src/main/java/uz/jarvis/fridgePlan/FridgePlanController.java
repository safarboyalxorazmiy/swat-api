package uz.jarvis.fridgePlan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fridge/plan")
@RequiredArgsConstructor
public class FridgePlanController {
  private final FridgePlanService fridgePlanService;

  @PreAuthorize("hasRole('ACCOUNTANT')")
  @PostMapping("/set")
  public ResponseEntity<Boolean> createPlan(
      @RequestParam Long modelId,
      @RequestParam Long plan
  ) {
    return ResponseEntity.ok(
        fridgePlanService.create(modelId, plan)
    );
  }
}