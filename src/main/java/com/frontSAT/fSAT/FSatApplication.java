package com.frontSAT.fSAT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FSatApplication extends ServletInitializer implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FSatApplication.class, args);
	}
	@Autowired
	PasswordEncoder encoder;
	@Override
	public void run(String... args) throws Exception{
		System.out.println(encoder.encode("azerty"));
	}
}
