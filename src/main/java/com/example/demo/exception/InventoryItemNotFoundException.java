package com.example.demo.exception;

public class InventoryItemNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -632004375578560245L;

	public InventoryItemNotFoundException(int id) {

        super(String.format("Product with Id %d not found in inventory", id));
    }
}
