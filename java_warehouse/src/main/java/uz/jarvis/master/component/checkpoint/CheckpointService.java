package uz.jarvis.master.component.checkpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jarvis.master.line.MasterLineEntity;
import uz.jarvis.master.line.MasterLineService;
import uz.jarvis.master.line.dto.LineInfoDTO;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckpointService {
  private final CheckpointRepository checkpointRepository;
  private final MasterLineService masterLineService;

  public CheckpointInfoDTO getLineInfo(Long masterId) {
    LineInfoDTO lineByMasterId = masterLineService.getLineByMasterId(masterId);
    if (lineByMasterId.getName().equals("LINYA YO'Q")) {
      throw new LineNotFoundException("LINYA YO'Q");
    }

    Optional<CheckpointEntity> byLineId =
      checkpointRepository.findById(lineByMasterId.getId());

    if (byLineId.isEmpty()) {
      throw new LineNotFoundException("LINYA YO'Q");
    }

    CheckpointEntity checkpointEntity = byLineId.get();
    CheckpointInfoDTO info = new CheckpointInfoDTO();
    info.setId(checkpointEntity.getId());
    info.setName(checkpointEntity.getName());
    info.setStatus(checkpointEntity.getStatus());
    info.setPhoto(checkpointEntity.getPhoto());
    info.setAddress(checkpointEntity.getAddress());
    info.setIsCompositeCreatable(checkpointEntity.getIsCompositeCreatable());
    return info;
  }
}