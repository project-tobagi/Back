package hello.tobagi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.example.storage", "hello.tobagi.tobagi"})
public class TobagiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TobagiApplication.class, args);
	}

}
