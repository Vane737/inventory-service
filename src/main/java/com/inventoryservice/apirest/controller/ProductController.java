package com.inventoryservice.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventoryservice.apirest.model.Category;
import com.inventoryservice.apirest.model.DetailProduct;
import com.inventoryservice.apirest.model.Product;
import com.inventoryservice.apirest.repository.CategoryRepository;
import com.inventoryservice.apirest.repository.ProductRepository;

@RestController
@CrossOrigin
@RequestMapping("api/v1/")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    @GetMapping("products")
    List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    @GetMapping("products/{id}")
    DetailProduct getProductsById(@PathVariable String id) {
        return productRepository.findById(id)
            .map(product -> {
                Category category = categoryRepository.findById(product.getIdCategory())
                        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
                return new DetailProduct(product, category);
            })
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
}


    @PostMapping("products")
    Product setProducts(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("products/{id}")
    Product updateProducts(@PathVariable String id, @RequestBody Product product) {
        Product productToUpdate = productRepository.findById(id).orElse(null);
        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setCode(product.getCode());
        productToUpdate.setUnitMeasurement(product.getUnitMeasurement());
        productToUpdate.setBrand(product.getBrand());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setStock(product.getStock());
        productToUpdate.setIdCategory(product.getIdCategory());
        return productRepository.save(productToUpdate);
    }

    @DeleteMapping("products/{id}")
    String deleteProducts(@PathVariable String id) {
        productRepository.deleteById(id);
        return id;
    }

}
