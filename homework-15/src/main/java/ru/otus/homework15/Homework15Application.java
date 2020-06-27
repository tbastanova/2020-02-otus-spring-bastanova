package ru.otus.homework15;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import ru.otus.homework15.service.LifeCycleService;

@IntegrationComponentScan
@ComponentScan
@EnableIntegration
public class Homework15Application {

    public static void main(String[] args) {
        SpringApplication.run(Homework15Application.class, args);

        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(Homework15Application.class);
        LifeCycleService lifeCycleService = ctx.getBean(LifeCycleService.class);
        lifeCycleService.run();
    }

}
