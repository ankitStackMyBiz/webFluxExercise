package com.example.webfluxdemo.mongodb.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private int quantity;
    private double price;
}
