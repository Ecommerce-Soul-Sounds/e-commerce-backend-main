package com.revature.repositories;

import com.revature.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM product WHERE brand = ?1", nativeQuery = true)
    List<Product> getProductsByBrand(String brand);

    @Query(value = "SELECT * FROM product WHERE category = ?1", nativeQuery = true)
    List<Product> getProductsByCategory(String category);

    
}
