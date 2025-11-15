package com.sweet_shop_server.sweet_shop_server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SweetDTO {
    private Long id;
    private String name;
    private String category;
    private Double price;
    private Integer quantity;
}

