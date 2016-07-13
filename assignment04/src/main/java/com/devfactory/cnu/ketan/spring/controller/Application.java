package com.devfactory.cnu.ketan.spring.controller;

/**
 * Created by ketanpatil on 07/07/16.
 */

import com.devfactory.cnu.ketan.spring.controller.logDecorator.ExecuteTimeInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    @Autowired
    private ExecuteTimeInterceptor requestInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor);
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
