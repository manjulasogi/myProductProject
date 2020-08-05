package com.myretail.product.components;

import org.springframework.stereotype.Component;

import com.myretail.product.entities.Product;


public class ProductData {
	private int id;
	private String name;
	private String description;
	private PriceData currentPrice;
	private String key;
	
	
	public ProductData() {
	
	}
	public ProductData(int id, String name, String description, PriceData currentPrice, String key) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.currentPrice = currentPrice;
		this.key = key;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public PriceData getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(PriceData currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
