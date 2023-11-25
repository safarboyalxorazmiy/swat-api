package uz.jarvis.models;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelService {
  private final ModelRepository modelRepository;

  public List<ModelEntity> findByName(String name) {
    return modelRepository.findByNameLike(name + "%");
  }

  public ModelEntity findById(Long id) {
    return modelRepository.findById(id).get();
  }
}