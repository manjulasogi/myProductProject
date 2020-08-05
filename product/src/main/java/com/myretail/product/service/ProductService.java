package com.myretail.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myretail.product.components.Price;
import com.myretail.product.components.PriceData;
import com.myretail.product.components.ProductData;
import com.myretail.product.components.ProductPrice;
import com.myretail.product.entities.Product;
import com.myretail.product.exceptions.ProductNotFoundException;
import com.myretail.product.repositories.ProductRepository;


@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
			
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Product getProduct(int id ){
		return productRepository.findById(id).get();
	}
	
	public ProductPrice getProductPrice(int id, String key ){
		 //try {	
			 Product product = productRepository.findById(id).get();
		// }
		/// catch(Exception e) {
		//	return null;
		// }
		
		//String authToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNTk3ODAyNjQxLCJpYXQiOjE1OTY0NDA0NzV9.yv3wlX9iWXV-ugnmC05e2-xR9EkjDhtFBuGBZKwZ-Eg";
        System.out.println("key: "+key);
	    HttpHeaders headers = new HttpHeaders();
	   
	    headers.add("Authorization", "Bearer "+ key );
	    
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl
		  = "http://ec2-13-234-3-31.ap-south-1.compute.amazonaws.com:9997/product/" + id + "/price";
		//ResponseEntity<String> response  = restTemplate
			//	  .getForEntity(resourceUrl + id + "/price", String.class);
		
		ResponseEntity<Price> response = restTemplate.exchange(
				resourceUrl,
	            HttpMethod.GET,
	            new HttpEntity<>("parameters", headers),
	            Price.class
	    );
  
	
		ProductPrice productPrice = new ProductPrice();		
		productPrice.setId(product.getId());
		productPrice.setName(product.getName());
		productPrice.setDescription(product.getDescription());
		if(response.getBody() == null) {
			productPrice.setCurrentPrice(new PriceData(0,""));
		}
		else {
			productPrice.setCurrentPrice(new PriceData(response.getBody().getValue(),response.getBody().getCurrency()));
		}
		return productPrice;
	}
	
	public void addProduct(ProductData productData){
		String authToken = productData.getKey();
		Product product = null;
		try {
			product = productRepository.findById(productData.getId()).get();
			savePrice(productData, authToken);
		}
		catch(NoSuchElementException e) {
			//Create new product in product table
			Product newproduct = new Product(productData.getId(), productData.getName(), productData.getDescription());
			Product savedProduct = productRepository.save(newproduct);
			if(savedProduct.getId() == productData.getId()) {
				savePrice(productData, authToken);
			}
		}
			
			
	}
	
	public void savePrice(ProductData productData, String authToken)
	{
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://ec2-13-234-3-31.ap-south-1.compute.amazonaws.com:9997/product/" +productData.getId()  + "/price";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer "+authToken );
		
		ResponseEntity<PriceData> response = restTemplate.exchange(resourceUrl, HttpMethod.GET,
				new HttpEntity<>("parameters", headers), PriceData.class);
	
		if(response.getBody() == null) {
			PriceData priceData = productData.getCurrentPrice();
			Price price = new Price(productData.getId(), priceData.getValue(), priceData.getCurrency(), productData.getId());
		
			HttpEntity<Price> request = 
			      new HttpEntity<Price>(price, headers);
			//Create new price data in price table 
			Price newprice = restTemplate.postForObject(resourceUrl, request, Price.class);
		}
		else {
			throw new ProductNotFoundException();
	    	           
		}
		 
	}
	public void updateProduct(ProductData productData){
		Product product = new Product(productData.getId(), productData.getName(), productData.getDescription());
		productRepository.save(product);
		
		String authToken = productData.getKey();
		RestTemplate restTemplate = new RestTemplate();

		String resourceUrl = "http://ec2-13-234-3-31.ap-south-1.compute.amazonaws.com:9997/product/" +productData.getId()  + "/price";
	
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer "+authToken );
				
		
		ResponseEntity<Price> response = restTemplate.exchange(resourceUrl, HttpMethod.GET,
				new HttpEntity<>("parameters", headers), Price.class);
		
        if(response.getBody() != null) {
        	PriceData pdata = productData.getCurrentPrice();
        	Price newPrice = new Price(response.getBody().getPriceid(), pdata.getValue(), pdata.getCurrency(),response.getBody().getPid() );
        
			restTemplate.exchange(
					resourceUrl,
		            HttpMethod.PUT,
		            new HttpEntity<>(newPrice, headers),
		            Void.class
		    );
        }
        else {
        	PriceData pdata = productData.getCurrentPrice();
        	Price newPrice = new Price(productData.getId(), pdata.getValue(), pdata.getCurrency(),productData.getId() );
        	restTemplate.exchange(
					resourceUrl,
		            HttpMethod.PUT,
		            new HttpEntity<>(newPrice, headers),
		            Void.class
		    );
        }
	}
	
	public void deleteProduct(int id){
		
		productRepository.deleteById(id);
	}
	
	

}