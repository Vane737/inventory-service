package com.inventoryservice.apirest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inventoryservice.apirest.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
    
}
