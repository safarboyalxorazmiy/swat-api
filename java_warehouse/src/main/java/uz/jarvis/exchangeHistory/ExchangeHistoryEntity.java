package uz.jarvis.exchangeHistory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "exchange_history")
public class ExchangeHistoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long fromId;

  private Long toId;

  private ExchangeType exchangeType;

  private LocalDateTime createdDate;
}
