package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private int id;
    
    private int quantity;
    
    @Column(nullable = false)
    private double price;
    
    private String category;
    
    private String brand;

    @Lob
    private String description;
    
    private String image;
    
    private String name;
}
