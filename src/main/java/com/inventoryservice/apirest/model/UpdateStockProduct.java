package com.inventoryservice.apirest.model;

import lombok.Data;

@Data

public class UpdateStockProduct {
    private String idProduct;
    private int quantity;
}
