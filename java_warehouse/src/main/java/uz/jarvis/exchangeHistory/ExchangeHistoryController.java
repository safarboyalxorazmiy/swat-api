package uz.jarvis.exchangeHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jarvis.user.User;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exchange")
@RequiredArgsConstructor
public class ExchangeHistoryController {
  private final ExchangeHistoryService exchangeHistoryService;

  @GetMapping(
      "/get/history/{masterId}"
  )
  public ResponseEntity<List<ExchangeHistoryDTO>> getExchangeHistoryForMaster(@PathVariable Long masterId) {
    return ResponseEntity.ok(exchangeHistoryService.getExchangeHistoryForMaster(masterId));
  }

  @GetMapping("/get/history/{logistId}")
  public ResponseEntity<List<ExchangeHistoryDTO>> getExchangeHistoryForLogist(@PathVariable Long logistId) {
    return ResponseEntity.ok(exchangeHistoryService.getExchangeHistoryForLogist(logistId));
  }

  @PreAuthorize("hasAnyAuthority('MASTER', 'LOGIST')")
  @GetMapping("/get/history")
  public ResponseEntity<List<ExchangeHistoryDTO>> getExchangeHistory() {
    return ResponseEntity.ok(exchangeHistoryService.getExchangeHistory(getUserId()));
  }

  private Long getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();

    return user.getId();
  }
}