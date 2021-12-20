package com.aggregation.crm.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@PropertySource("classpath:web-server.properties")
public class WebServer {

    public static void main(String[] args) {
        SpringApplication.run(WebServer.class, args);
    }

}
