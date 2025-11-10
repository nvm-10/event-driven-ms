package com.nvm10.profile;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class ProfileApplication {

    private CommandGateway commandGateway;

    public static void main(String[] args) {
        SpringApplication.run(ProfileApplication.class, args);
    }

}
