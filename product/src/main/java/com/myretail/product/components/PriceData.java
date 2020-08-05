package com.myretail.product.components;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class PriceData {
	private int value;
	private String currency;
		
	public PriceData() {
	
	}
	public PriceData(int value, String currency) {
		this.value = value;
		this.currency = currency;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
