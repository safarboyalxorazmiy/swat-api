package uz.jarvis.exchangeHistory;

import jakarta.persistence.*;

@Entity
@Table(name = "exchange_history")
public class ExchangeHistoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long by;

  private ExchangeType exchangeType;

  private String message;
}
