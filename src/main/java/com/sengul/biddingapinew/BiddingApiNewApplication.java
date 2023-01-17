package com.sengul.biddingapinew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BiddingApiNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiddingApiNewApplication.class, args);
    }

}
