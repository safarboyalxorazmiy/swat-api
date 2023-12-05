package uz.jarvis.composite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompositeComponentCreateDTO {
  private CompositeCreateDTO component;
  private Double quantity;
}
