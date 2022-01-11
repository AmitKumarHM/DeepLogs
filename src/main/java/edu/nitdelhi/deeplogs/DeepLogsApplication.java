package edu.nitdelhi.deeplogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import edu.nitdelhi.deeplogs.configuration.FileStorageProperties;

@SpringBootApplication
@ComponentScan("edu.nitdelhi")
@Configuration
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class DeepLogsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeepLogsApplication.class, args);
	}

	public @Bean MongoClient mongoClient() {
	       return MongoClients.create("mongodb://localhost:27017");
	   }
}
