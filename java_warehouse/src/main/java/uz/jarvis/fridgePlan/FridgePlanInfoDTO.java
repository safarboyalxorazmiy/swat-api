package uz.jarvis.fridgePlan;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FridgePlanInfoDTO {
  private Long id;

  private String modelName;
  private String modelCode;
  private String modelComment;

  private Long modelId;
  private Long plan;
}