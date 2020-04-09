package com.sdaacademy.expert_in_action;

import org.aspectj.lang.annotation.AdviceName;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class ExpertInActionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpertInActionApplication.class, args);
    }

}
