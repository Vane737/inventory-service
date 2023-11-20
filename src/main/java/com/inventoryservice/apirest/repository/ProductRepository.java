package com.inventoryservice.apirest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inventoryservice.apirest.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    
}
