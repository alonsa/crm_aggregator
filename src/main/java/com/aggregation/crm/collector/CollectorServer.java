package com.aggregation.crm.collector;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@PropertySource("classpath:collector-server.properties")
public class CollectorServer {

    public static void main(String[] args) {
        SpringApplication.run(CollectorServer.class, args);
    }

}
