package uz.jarvis.exchangeHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeHistoryController {
  private final ExchangeHistoryService exchangeHistoryService;

  @GetMapping("/get/history/{masterId}")
  public ResponseEntity<List<ExchangeHistoryDTO>> getExchangeHistoryForMaster(@PathVariable Long masterId) {
    return ResponseEntity.ok(exchangeHistoryService.getExchangeHistoryForMaster(masterId));
  }

  @GetMapping("/get/history/{logistId}")
  public ResponseEntity<List<ExchangeHistoryDTO>> getExchangeHistoryForLogist(@PathVariable Long logistId) {
    return ResponseEntity.ok(exchangeHistoryService.getExchangeHistoryForLogist(logistId));
  }
}