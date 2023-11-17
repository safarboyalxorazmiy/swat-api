package uz.logist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import uz.jarvis.config.WebConfig;

@SpringBootApplication
public class LogistApplication {
  public static void main(String[] args) {
    SpringApplication.run(LogistApplication.class, args);
  }
}