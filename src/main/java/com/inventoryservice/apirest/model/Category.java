package com.inventoryservice.apirest.model;

import org.springframework.data.mongodb.core.mapping.Document;
import nonapi.io.github.classgraph.json.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Category {
    @Id
    private String id;
    private String name;
    private String description;
}
