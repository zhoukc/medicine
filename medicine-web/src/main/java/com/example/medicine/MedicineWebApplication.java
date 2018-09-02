package com.example.medicine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicineWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicineWebApplication.class, args);
    }
}
