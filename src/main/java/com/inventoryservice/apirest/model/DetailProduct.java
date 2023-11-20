package com.inventoryservice.apirest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

// import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
public class DetailProduct {
    private String id;
    private String name;
    private String description;
    private String code;
    private String brand;
    private String unitMeasurement;
    private double price;
    private int stock;
    private Category category;

    public DetailProduct(Product product, Category category) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.code = product.getCode();
        this.unitMeasurement = product.getUnitMeasurement();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.category = category;
    }

}
