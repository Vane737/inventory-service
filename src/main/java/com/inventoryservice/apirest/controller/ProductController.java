package com.inventoryservice.apirest.controller;

import java.util.ArrayList;
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
import com.inventoryservice.apirest.model.UpdateStockProduct;
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
    
    @GetMapping("product/{id}")
    DetailProduct getProductsById(@PathVariable String id) {
        return productRepository.findById(id)
            .map(product -> {
                Category category = categoryRepository.findById(product.getIdCategory())
                        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
                return new DetailProduct(product, category);
            })
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
}


    @PostMapping("product")
    Product setProducts(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("product/{id}")
    Product updateProduct(@PathVariable String id, @RequestBody Product product) {
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

    @DeleteMapping("product/{id}")
    String deleteProducts(@PathVariable String id) {
        productRepository.deleteById(id);
        return id;
    }

    @PostMapping("/load-products")
    public void loadProducts(@RequestBody List<Product> products) {
        productRepository.saveAll(products);
    }

    @PutMapping("products/{id}/stock")
    public Product updateStock(@PathVariable String id, @RequestBody int quantity) {
        // Obtener el producto de la base de datos
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    
        // Actualizar el stock según la operación (puede ser positivo para reposición o negativo para venta)
        int newStock = product.getStock() + quantity;
        if (newStock < 0) {
            throw new RuntimeException("No hay suficiente stock para la venta");
        }
    
        // Actualizar el stock en el objeto producto
        product.setStock(newStock);
    
        // Guardar el producto actualizado en la base de datos
        return productRepository.save(product);
    }

    @PutMapping("products/update-stocks")
    public List<Product> updateStocks(@RequestBody List<UpdateStockProduct> updateProduct) {

        List<Product> updatedProducts = new ArrayList<>();

        for (UpdateStockProduct update : updateProduct) {
            String idProduct = update.getIdProduct();
            int quantity = update.getQuantity();

            Product product = productRepository.findById(idProduct)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            int newStock = product.getStock() + quantity;
            if (newStock < 0) {
                throw new RuntimeException("No hay suficiente stock para la venta");
            }

            product.setStock(newStock);
            updatedProducts.add(productRepository.save(product));
        }

        return updatedProducts;
    }
}
