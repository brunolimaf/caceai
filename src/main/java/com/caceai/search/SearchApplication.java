package com.caceai.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SearchApplication {

	public static void main(String[] args) {

		SpringApplication.run(SearchApplication.class, args);
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> webServerFactoryCustomizer() {
		return factory -> {
			String port = System.getenv("PORT");
			if (port != null) {
				factory.setPort(Integer.parseInt(port));
			}
		};
	}

}


//javac -d bin src/main/java/com/caceai/search/SearchApplication.java