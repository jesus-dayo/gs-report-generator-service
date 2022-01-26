package com.example.demo;

import com.example.demo.kafka.Greeting;
import com.example.demo.kafka.MessageListener;
import com.example.demo.kafka.MessageProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReportGeneratorApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ReportGeneratorApplication.class, args);

		ConfigurableApplicationContext context = SpringApplication.run(ReportGeneratorApplication.class, args);

		MessageProducer messageProducer = context.getBean(MessageProducer.class);
		MessageListener messageListener = context.getBean(MessageListener.class);

		String json = "{\n" +
				"  \"templateId\": 1,\n" +
				"  \"requestedBy\":  \"userId\",\n" +
				"  \"data\": [\n" +
				"    {\n" +
				"      \"transactionId\": \"12345\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"transactionId\": \"12346\"\n" +
				"    }\n" +
				"  ]\n" +
				"}";
//		messageProducer.sendMessage(new Greeting("Greetings", "World!"));

		messageProducer.sendMessage(json);
	}

//	@Bean
//	public MessageProducer messageProducer() {
//		return new MessageProducer();
//	}

//	@Bean
//	public MessageListener messageListener() {
//		return new MessageListener();
//	}

}
