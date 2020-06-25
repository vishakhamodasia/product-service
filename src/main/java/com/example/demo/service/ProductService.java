package com.example.demo.service;

import java.util.Optional;

import com.example.demo.model.ProductDB;

public interface ProductService {
	Optional<ProductDB> getProductById(int id);
}
