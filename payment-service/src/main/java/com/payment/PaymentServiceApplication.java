package com.payment;

import com.payment.service.TransactionService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@ComponentScan({"com.payment.controller", "com.payment.service", "com.payment.factory","com.payment.messagehandler"})
@EntityScan("com.payment.entity")
@EnableJpaRepositories("com.payment.repository")
@EnableFeignClients("com.payment.feignclient")
@EnableDiscoveryClient
public class PaymentServiceApplication {


	public static void main(String[] args) {


		SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
