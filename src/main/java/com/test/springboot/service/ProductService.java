package com.test.springboot.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.test.springboot.domain.Product;

@Service
public class ProductService {

	@Autowired
	RestTemplate restTemplate;
	
	public List<Product> findProductsByIds(List<Long> ids) {
		Product[] products = restTemplate.postForObject("http://zuul.192.168.0.9.nip.io:32001/api/product/ids", ids, Product[].class);
		return Arrays.stream(products).collect(Collectors.toList());
	}
}
