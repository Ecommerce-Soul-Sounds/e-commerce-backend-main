package com.revature.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Cart;

@Transactional
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    // update cart with
    @Query(value = "UPDATE cart SET total_quantity = ?1, dateModified = ?2 WHERE cart_id = ?3", nativeQuery = true)
    boolean updateCart(LocalDate dateModified, int totalQuantity, int cartId);
}
