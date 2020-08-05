package com.myretail.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.myretail.product.exceptions.ProductNotFoundException;

@ControllerAdvice
public class ProductExceptionController {
	//@ExceptionHandler({ProductNotFoundException.class})
	public ResponseEntity<Object> noProductexception(ProductNotFoundException exception){
		return new ResponseEntity<>("Specified Product does not exists.", HttpStatus.BAD_REQUEST);
	}

}
