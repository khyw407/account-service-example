package com.test.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.test.springboot.domain.Account;
import com.test.springboot.repository.AccountRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountServiceApplication {
	
	@LoadBalanced
	@Bean
	RestTemplate restTempkate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}
	
	@Bean
	AccountRepository accountRepository() {
		AccountRepository accountRepository = new AccountRepository();
		accountRepository.add(new Account("1234567890", 50000, 1L));
		accountRepository.add(new Account("1234567891", 50000, 1L));
		accountRepository.add(new Account("1234567892", 0, 1L));
		accountRepository.add(new Account("1234567893", 50000, 2L));
		accountRepository.add(new Account("1234567894", 0, 2L));
		accountRepository.add(new Account("1234567895", 50000, 2L));
		accountRepository.add(new Account("1234567896", 0, 3L));
		accountRepository.add(new Account("1234567897", 50000, 3L));
		accountRepository.add(new Account("1234567898", 50000, 3L));
		
		return accountRepository;
	}
}
