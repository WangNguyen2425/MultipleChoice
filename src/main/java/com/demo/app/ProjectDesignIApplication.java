package com.demo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.demo")
@EntityScan(basePackages = {"com.demo.app.model"})
@EnableJpaRepositories(basePackages = {"com.demo.app.repository"})
public class ProjectDesignIApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectDesignIApplication.class, args);
    }
}
