package com.adominguez.coderocks;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "APIs", version = "1.0", description = "Documentation APIs v1.0"))
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	CommandLineRunner run(UserRepository userRepository) {
//		return args -> {
//			userRepository.deleteAll()
//					.thenMany(Flux.just(
//							new User("Dhiraj", 23, 3456),
//							new User("Mike", 34, 3421),
//							new User("Hary", 21, 8974)
//					)
//							.flatMap(userRepository::save))
//					.thenMany(userRepository.findAll())
//					.subscribe(System.out::println);
//
//		};
//	}
}
