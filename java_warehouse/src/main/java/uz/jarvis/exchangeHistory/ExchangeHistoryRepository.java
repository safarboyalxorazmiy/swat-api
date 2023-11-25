package uz.jarvis.exchangeHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistoryEntity, Long> {
  @Query("from ExchangeHistoryEntity where toId=?1 or fromId=?1")
  List<ExchangeHistoryEntity> findByMasterId(Long masterId);

  @Query("from ExchangeHistoryEntity where fromId=?1")
  List<ExchangeHistoryEntity> findByLogistId(Long masterId);

  @Query("from ExchangeHistoryEntity where toId=?1 or fromId=?1")
  List<ExchangeHistoryEntity> findByUserId(Long userId);

  /*
  @Query("from ExchangeHistoryEntity where " +
    "to=?1 and exchangeType =?2 or from=?1 and exchangeType =?2")
  List<ExchangeHistoryEntity> findByMasterIdAndExchangeType(Long masterId, ExchangeType exchangeType);*/
}