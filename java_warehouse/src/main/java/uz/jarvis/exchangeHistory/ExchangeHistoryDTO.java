package uz.jarvis.exchangeHistory;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExchangeHistoryDTO {
  private Long from;
  private Long to;
  private ExchangeType exchangeType;
  private LocalDateTime createdDate;
}