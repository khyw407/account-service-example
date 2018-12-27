package com.test.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.springboot.domain.Account;
import com.test.springboot.domain.Order;
import com.test.springboot.repository.AccountRepository;
import com.test.springboot.service.AccountService;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Processor.class)
public class AccountServiceApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceApplication.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	AccountService accountService;
	
	@LoadBalanced
	@Bean
	RestTemplate restTempkate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}
	
	@StreamListener(Processor.INPUT)
	public void receiveOrder(Order order) throws JsonProcessingException {
		LOGGER.info("Order received: {}", objectMapper.writeValueAsString(order));
		accountService.process(order);
	}
	
	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
	    CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
	    loggingFilter.setIncludePayload(true);
	    loggingFilter.setIncludeHeaders(true);
	    loggingFilter.setMaxPayloadLength(1000);
	    loggingFilter.setAfterMessagePrefix("REQ:");
	    return loggingFilter;
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
