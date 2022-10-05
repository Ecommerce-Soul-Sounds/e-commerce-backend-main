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

    @Modifying
    @Query(value = "UPDATE product SET brand=?1, category=?2, description=?3, image=?4, name=?5, price=?6, quantity=?7 WHERE product_id=?8", nativeQuery = true)
    int updateProduct(String brand, String category, String description, String image, String name, double price, int quantity, int productId);
}
