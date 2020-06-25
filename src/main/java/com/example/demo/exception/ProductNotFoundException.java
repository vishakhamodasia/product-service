package com.example.demo.exception;

public class ProductNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -4596203671186513968L;

	public ProductNotFoundException(int id) {

        super(String.format("Product with Id %d not found", id));
    }
}
