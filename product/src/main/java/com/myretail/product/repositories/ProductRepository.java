package com.myretail.product.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myretail.product.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
}
