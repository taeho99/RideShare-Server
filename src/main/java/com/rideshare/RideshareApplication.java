package com.rideshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RideshareApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideshareApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOriginPatterns("*")
						.allowedMethods("*")
						.allowedHeaders("X-AUTH-TOKEN", "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
						.exposedHeaders("Content-Disposition", "X-AUTH-TOKEN", "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
						.allowCredentials(true);
			}
		};
	}
}
