package com.revature.dtos;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    private int id;
    
    private int quantity;
    
    private double price;
    
    private String category;
    
    private String brand;
    
    private String description;
    
    private String image;
    
    private String name;
}
