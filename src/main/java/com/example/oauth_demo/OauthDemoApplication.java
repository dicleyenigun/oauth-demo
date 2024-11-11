package com.example.oauth_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.example.oauth_demo") // Ana uygulama sınıfı
public class OauthDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthDemoApplication.class, args);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "password"; // Şifrelenmek istenilen parola
		String encodedPassword = encoder.encode(rawPassword);

		System.out.println("Encoded Password: " + encodedPassword);


	}


}
