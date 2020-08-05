package com.myretail.product.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.myretail.product.service.ProductService;
import com.myretail.product.components.ProductData;
import com.myretail.product.components.ProductPrice;
import com.myretail.product.entities.Product;
import com.myretail.product.exceptions.ProductNotFoundException;

@CrossOrigin
@RestController
public class ProductsController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private HttpServletRequest request;
	
	@CrossOrigin
	@RequestMapping("/products")
	public List<Product> getAllProducts(){
		return productService.getAllProducts();
	}

		
	@CrossOrigin
	@RequestMapping(value = "/async/products/{id}", method = RequestMethod.GET)
	@Async
	public CompletableFuture<ProductPrice> getProductASync(@PathVariable(name = "id") int id, @RequestHeader("Authorization") String key) {
		return CompletableFuture.completedFuture(productService.getProductPrice(id, key));
	}
	
	@CrossOrigin
	@RequestMapping("/products/{id}")
	public ProductPrice getProductPrice(@PathVariable ("id") int id, @RequestParam("key") String key){
	  //try {
			ProductPrice productPrice = productService.getProductPrice(id, key);
			return productPrice;
		//}
	    /*catch(Exception e) {
	    	throw new ResponseStatusException(
	    	           HttpStatus.NOT_FOUND, "Product Not Found", e);
		
		}*/
	}
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.POST,  value="/products/{id}")
	public void addProduct(@RequestBody ProductData productData){
		System.out.println("Product"+productData.toString());
		productService.addProduct(productData);
	}
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.PUT,  value="/products/{id}")
	public void updateProduct(@RequestBody ProductData product, @PathVariable long id){
		productService.updateProduct(product);
	}
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.DELETE,  value="/products/{id}")
	public void deleteProduct(@PathVariable int id){
		productService.deleteProduct(id);
	}
	
	
}
