package uz.jarvis.master.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jarvis.master.component.checkpoint.CheckpointEntity;
import uz.jarvis.master.component.checkpoint.CheckpointRepository;
import uz.jarvis.master.line.dto.LineInfoDTO;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MasterLineService {
  private final MasterLineRepository masterLineRepository;
  private final CheckpointRepository checkpointRepository;

  public Boolean create(Long masterId, Integer lineId) {
    Set<Integer> lines = getLines();
    boolean contains = lines.contains(lineId);
    if (!contains) {
      return false;
    }

    Optional<MasterLineEntity> byMasterId = masterLineRepository.findByMasterId(masterId);
    if (byMasterId.isPresent()) {
      MasterLineEntity masterLineEntity = byMasterId.get();
      masterLineEntity.setLineId(lineId);
      masterLineRepository.save(masterLineEntity);

      return true;
    }

    MasterLineEntity masterLineEntity = new MasterLineEntity();
    masterLineEntity.setMasterId(masterId);
    masterLineEntity.setLineId(lineId);
    masterLineRepository.save(masterLineEntity);
    return true;
  }

  public Set<Integer> getLines() {
    Set<Integer> lines = new HashSet<>();
    lines.add(1);
    lines.add(2);
    lines.add(9);
    lines.add(10);
    lines.add(11);
    lines.add(12);
    lines.add(13);
    lines.add(19);
    lines.add(20);
    lines.add(21);
    lines.add(27);
    return lines;
  }

  public List<LineInfoDTO> getLinesInfo() {
    List<CheckpointEntity> all = checkpointRepository.findAll();
    List<LineInfoDTO> result = new LinkedList<>();
    for (CheckpointEntity checkpointEntity : all) {
      LineInfoDTO info = new LineInfoDTO();
      info.setId(checkpointEntity.getId());
      info.setName(checkpointEntity.getName());
      result.add(info);
    }
    return result;
  }

  private String getNameByLineId(Integer lineId) {
    Optional<CheckpointEntity> byId = checkpointRepository.findById(lineId);
    return byId.get().getName();
  }

  public LineInfoDTO getLineByMasterId(Long masterId) {
    Optional<MasterLineEntity> byMasterId = masterLineRepository.findByMasterId(masterId);
    if (byMasterId.isEmpty()) {
      LineInfoDTO lineInfoDTO = new LineInfoDTO();
      lineInfoDTO.setId(0);
      lineInfoDTO.setName("LINYA YO'Q");
      return lineInfoDTO;
    }

    MasterLineEntity masterLineEntity = byMasterId.get();
    LineInfoDTO lineInfoDTO = new LineInfoDTO();
    lineInfoDTO.setId(masterLineEntity.getLineId());
    lineInfoDTO.setName(getNameByLineId(masterLineEntity.getLineId()));
    return lineInfoDTO;
  }
}