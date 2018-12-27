package com.test.springboot.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.springboot.domain.Account;
import com.test.springboot.domain.Order;
import com.test.springboot.domain.Product;
import com.test.springboot.repository.AccountRepository;

@Service
public class AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderSender orderSender;
	
	public void process(final Order order) throws JsonProcessingException {
		LOGGER.info("Order processed: {}", objectMapper.writeValueAsString(order));
		
		List<Account> accounts =  accountRepository.findByCustomer(order.getCustomerId());
		Account account = accounts.get(0);
		LOGGER.info("Account found: {}", objectMapper.writeValueAsString(account));
		
		List<Product> products = productService.findProductsByIds(order.getProductIds());
		LOGGER.info("Products found: {}", objectMapper.writeValueAsString(products));
		
		products.forEach(p -> order.setPrice(order.getPrice() + p.getPrice()));
		
		if (order.getPrice() <= account.getBalance()) {
			order.setStatus(OrderStatus.ACCEPTED);
			account.setBalance(account.getBalance() - order.getPrice());
		} else {
			order.setStatus(OrderStatus.REJECTED);
		}
		
		orderSender.send(order);
		
		LOGGER.info("Order response sent: {}", objectMapper.writeValueAsString(order));
	}
}
