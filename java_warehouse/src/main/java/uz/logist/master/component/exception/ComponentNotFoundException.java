package uz.logist.master.component.exception;

public class ComponentNotFoundException extends RuntimeException {
  public ComponentNotFoundException(String message) {
    super(message);
  }
}
