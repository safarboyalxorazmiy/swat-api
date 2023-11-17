package uz.jarvis.config.jwt;

public class JwtParseException extends RuntimeException {
  public JwtParseException(String message) {
    super(message);
  }

  public JwtParseException(String message, Throwable cause) {
    super(message, cause);
  }
}
