package com.inventoryservice.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventoryservice.apirest.model.Category;
import com.inventoryservice.apirest.repository.CategoryRepository;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin
@RequestMapping("api/v1/")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("categories")
    List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    @GetMapping("category/{id}")
    Category getCategoryById(@PathVariable String id) {
        return categoryRepository.findById(id).orElse(null);    
    }

    @PostMapping("categories")
    Category setCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("category/{id}")
    Category updateCategory(@PathVariable String id, @RequestBody Category category) {
        Category categoryToUpdate = categoryRepository.findById(id).orElse(null);
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setDescription(category.getDescription());
        return categoryRepository.save(categoryToUpdate);
    }

    @DeleteMapping("category/{id}")
    String deleteCategory(@PathVariable String id) {
        categoryRepository.deleteById(id);
        return id;
    }

    @PostMapping("/loadcategories")
    public void loadcategory(@RequestBody List<Category> categories) {
        categoryRepository.saveAll(categories);
    }






}
