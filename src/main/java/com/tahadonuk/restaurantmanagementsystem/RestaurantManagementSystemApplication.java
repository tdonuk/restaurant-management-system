package com.tahadonuk.restaurantmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;

import java.util.Properties;

@SpringBootApplication(exclude = {R2dbcAutoConfiguration.class})
public class RestaurantManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RestaurantManagementSystemApplication.class);

        Properties props = new Properties();

        props.put("spring.datasource.username","postgres");
        props.put("spring.datasource.password","password");
        props.put("spring.datasource.driver-class-name","org.postgresql.Driver");
        props.put("spring.datasource.url","jdbc:postgresql://localhost:5432/postgres");
        props.put("spring.jpa.generate-ddl", "true");
        props.put("spring.jpa.hibernate.ddl-auto","update");

        app.setDefaultProperties(props);

        app.run(args);

    }

}
