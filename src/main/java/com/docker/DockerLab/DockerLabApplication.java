package com.docker.DockerLab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })

public class DockerLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerLabApplication.class, args);
	}

}
