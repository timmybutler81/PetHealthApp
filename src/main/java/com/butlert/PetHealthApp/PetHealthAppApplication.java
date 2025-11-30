package com.butlert.PetHealthApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PetHealthAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetHealthAppApplication.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner(PetHealthAppCli cli) {
        return args -> cli.start();
    }

}
