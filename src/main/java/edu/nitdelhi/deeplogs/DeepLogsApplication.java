package edu.nitdelhi.deeplogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("edu.nitdelhi")
public class DeepLogsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeepLogsApplication.class, args);
	}

}
