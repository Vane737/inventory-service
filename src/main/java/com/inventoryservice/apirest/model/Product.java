package com.inventoryservice.apirest.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private String code;
    private String brand;
    private String unitMeasurement;
    private double price;
    private int stock;
    private String idCategory;

}
