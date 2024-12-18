package com.hd.hd_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HdApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(HdApplication.class, args);
    }
}
