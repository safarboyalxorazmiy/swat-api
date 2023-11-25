package uz.jarvis.exchangeHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeHistoryService {
  private final ExchangeHistoryRepository exchangeHistoryRepository;

  public void create(ExchangeType exchangeType, Long fromId, Long toId) {
    ExchangeHistoryEntity exchangeHistory = new ExchangeHistoryEntity();
    exchangeHistory.setExchangeType(exchangeType);
    exchangeHistory.setFromId(fromId);
    exchangeHistory.setToId(toId);

    exchangeHistoryRepository.save(exchangeHistory);
  }


  public List<ExchangeHistoryDTO> getExchangeHistoryForMaster(Long masterId) {
    List<ExchangeHistoryEntity> byMasterId = exchangeHistoryRepository.findByMasterId(masterId);

    List<ExchangeHistoryDTO> result = new ArrayList<>();
    for (ExchangeHistoryEntity entity : byMasterId) {
      ExchangeHistoryDTO dto = new ExchangeHistoryDTO();
      dto.setFrom(entity.getFromId());
      dto.setTo(entity.getToId());
      dto.setCreatedDate(entity.getCreatedDate());
      dto.setExchangeType(entity.getExchangeType());
    }

    return result;
  }

  public List<ExchangeHistoryDTO> getExchangeHistoryForLogist(Long logistId) {
    List<ExchangeHistoryEntity> byMasterId = exchangeHistoryRepository.findByLogistId(logistId);

    List<ExchangeHistoryDTO> result = new ArrayList<>();
    for (ExchangeHistoryEntity entity : byMasterId) {
      ExchangeHistoryDTO dto = new ExchangeHistoryDTO();
      dto.setFrom(entity.getFromId());
      dto.setTo(entity.getToId());
      dto.setCreatedDate(entity.getCreatedDate());
      dto.setExchangeType(entity.getExchangeType());
    }

    return result;
  }

  public List<ExchangeHistoryDTO> getExchangeHistory(Long userId) {
    List<ExchangeHistoryEntity> byUserId = exchangeHistoryRepository.findByUserId(userId);

    List<ExchangeHistoryDTO> result = new ArrayList<>();
    for (ExchangeHistoryEntity entity : byUserId) {
      ExchangeHistoryDTO dto = new ExchangeHistoryDTO();
      dto.setFrom(entity.getFromId());
      dto.setTo(entity.getToId());
      dto.setCreatedDate(entity.getCreatedDate());
      dto.setExchangeType(entity.getExchangeType());
    }

    return result;
  }

}