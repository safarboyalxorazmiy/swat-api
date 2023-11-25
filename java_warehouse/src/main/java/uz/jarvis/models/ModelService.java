package uz.jarvis.models;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelService {
  private final ModelRepository modelRepository;

  public ModelEntity findById(Long id) {
    return modelRepository.findById(id).get();
  }

  public List<ModelInfoDTO> findByNameLike(String name) {
    List<ModelEntity> byNameLike = modelRepository.findByNameLike(name + "%");

    List<ModelInfoDTO> result = new ArrayList<>();
    for (ModelEntity modelEntity : byNameLike) {
      ModelInfoDTO dto = new ModelInfoDTO();
      dto.setId(modelEntity.getId());
      dto.setName(modelEntity.getName());
      dto.setCode(modelEntity.getCode());
      dto.setComment(modelEntity.getComment());
      dto.setStatus(modelEntity.getStatus());
      dto.setTime(modelEntity.getTime());
      dto.setAssembly(modelEntity.getAssembly());
      result.add(dto);
    }

    return result;
  }
}