package com.aggregation.crm.repository;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication()
@PropertySource("classpath:repository-server.properties")
public class RepositoryServer {

    public static void main(String[] args) {
        SpringApplication.run(RepositoryServer.class, args);
    }

}
