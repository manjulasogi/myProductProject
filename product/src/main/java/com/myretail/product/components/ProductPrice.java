package com.myretail.product.components;

import javax.persistence.Id;

import org.springframework.stereotype.Component;

public class ProductPrice {
	private int id;
	private String name;
	private String description;
	private PriceData currentPrice;
	
	
	public ProductPrice() {
	
	}
	public ProductPrice(int id, String name, String description, PriceData currentPrice) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.currentPrice = currentPrice;
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

}
