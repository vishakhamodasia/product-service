package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="inventory-service")
public interface InventoryFeignClient {

	@GetMapping("/inventory/{id}")
	public ResponseEntity<Integer> getAvailabilityCountByPId(@PathVariable int id);
}
