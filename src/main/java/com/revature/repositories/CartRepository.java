package com.revature.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Cart;

@Transactional
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    // update cart with
    @Query(value = "UPDATE cart SET date_modified = ?1, total_quantity = ?2 WHERE cart_id = ?3", nativeQuery = true)
    @Modifying
    int updateCart(LocalDate dateModified, int totalQuantity, int cartId);
}
