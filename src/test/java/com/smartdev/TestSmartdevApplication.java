package com.smartdev;

import org.springframework.boot.SpringApplication;

public class TestSmartdevApplication {

    public static void main(String[] args) {
        SpringApplication.from(SmartdevApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
