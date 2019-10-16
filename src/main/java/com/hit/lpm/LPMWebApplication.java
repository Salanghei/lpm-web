package com.hit.lpm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.wf.jwtp.configuration.EnableJwtPermission;

@EnableJwtPermission
@SpringBootApplication
public class LPMWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(LPMWebApplication.class, args);
    }
}
