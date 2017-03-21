package com.tdt.musicapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.tdt.musicapp")
public class Tunedoorajsv2Application extends SpringBootServletInitializer {


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Tunedoorajsv2Application.class);
	}


	public static void main(String[] args) {
		SpringApplication.run(Tunedoorajsv2Application.class, args);
	}
}
