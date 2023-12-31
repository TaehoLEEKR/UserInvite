package com.example.userinviteassgin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserInviteAssginApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserInviteAssginApplication.class, args);
    }

}
