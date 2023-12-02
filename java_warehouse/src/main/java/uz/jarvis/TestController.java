package uz.jarvis;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jarvis.components.ComponentsService;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
  private final ComponentsService componentsService;


}