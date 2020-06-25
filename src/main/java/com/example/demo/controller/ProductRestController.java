package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.client.InventoryFeignClient;
import com.example.demo.exception.InventoryItemNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.model.ProductDB;
import com.example.demo.service.ProductService;

import brave.sampler.Sampler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductRestController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	InventoryFeignClient inventoryFeignClient;
	
	@Bean
	public Sampler defaultSampler() {
	    return Sampler.ALWAYS_SAMPLE;
	}
	
	@GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable int id){
		log.info("Inside product service..");
		ProductDB p=null;
		ResponseEntity<Integer> count;
		Product product=null;
		Optional<ProductDB> productdb = productService.getProductById(id);
		if(productdb.isPresent()) {
			p= productdb.get();
			count=inventoryFeignClient.getAvailabilityCountByPId(id);
			if(count.getBody().intValue() == -1) {
				throw new InventoryItemNotFoundException(id);
			}
			product= new Product(id,p.getProductName(),p.getProductDetails(),count.getBody().intValue());
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
		
		throw new ProductNotFoundException(id);      
    }

}
