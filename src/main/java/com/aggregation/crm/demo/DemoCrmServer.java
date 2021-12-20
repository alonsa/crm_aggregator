package com.aggregation.crm.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@PropertySource("classpath:demo-crm-server.properties")
public class DemoCrmServer {

    public static void main(String[] args) {
        SpringApplication.run(DemoCrmServer.class, args);
    }

}
