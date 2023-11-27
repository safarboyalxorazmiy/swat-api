package uz.jarvis.fridgePlan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fridge/plan")
@RequiredArgsConstructor
public class FridgePlanController {
  private final FridgePlanService fridgePlanService;

  @PreAuthorize("hasRole('ACCOUNTANT')")
  @PostMapping("/create")
  public ResponseEntity<Boolean> createPlan(
      @RequestParam Long modelId,
      @RequestParam Long plan
  ) {
    return ResponseEntity.ok(
        fridgePlanService.create(modelId, plan)
    );
  }

  @PreAuthorize("hasRole('ACCOUNTANT')")
  @PutMapping("/update")
  public ResponseEntity<Boolean> updatePlan(
      @RequestParam Long modelId,
      @RequestParam Long plan
  ) {
    return ResponseEntity.ok(
        fridgePlanService.update(modelId, plan)
    );
  }

  @PreAuthorize("hasRole('ACCOUNTANT')")
  @DeleteMapping("/delete")
  public ResponseEntity<Boolean> deletePlan(
      @RequestParam Long modelId,
      @RequestParam Long plan
  ) {
    return ResponseEntity.ok(
        fridgePlanService.delete(modelId, plan)
    );
  }

  @PreAuthorize("hasRole('ACCOUNTANT')")
  @GetMapping("/get")
  public ResponseEntity<List<FridgePlanInfoDTO>> getPlans() {
    return ResponseEntity.ok(
        fridgePlanService.findAll()
    );
  }
}