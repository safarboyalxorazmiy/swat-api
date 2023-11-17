package uz.logist.exchangeHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistoryEntity, Long> {
}