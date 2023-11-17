package uz.logist.master.component.exception;

public class QuantityNotEnoughException extends RuntimeException {
  public QuantityNotEnoughException(String message) {
    super(message);
  }
}
