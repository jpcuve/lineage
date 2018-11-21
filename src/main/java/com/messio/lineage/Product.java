package com.messio.lineage;

import java.math.BigDecimal;

public class Product {
    private final int code;
    private final String name;
    private final String description;
    private final int price;

    public Product(int code) {
        this.code = code;
        this.name = "Product " + code;
        this.description = "Description " + code;
        this.price = 100 + code;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public static Product newInstance(int i){
        return new Product(i);
    }

}
