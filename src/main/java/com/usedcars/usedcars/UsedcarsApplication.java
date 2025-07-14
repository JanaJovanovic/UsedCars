package com.usedcars.usedcars;

import com.usedcars.usedcars.auth.AuthenticationService;
import com.usedcars.usedcars.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsedcarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsedcarsApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AuthenticationService service, UserRepository repository) {
		return args -> {
			service.registerAdmin();
		};
	}
}
