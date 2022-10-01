package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.CartItem;
@Transactional
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    //get all by cart id
    @Query(value = "SELECT * FROM cart_item WHERE cart_id = ?1", nativeQuery = true)
    List<CartItem> getAllByCartId(int cartId);

    @Query(value = "UPDATE cart_item quantity = ?1 WHERE cart_item_id = ?2", nativeQuery = true)
    boolean updateItem(int quantity, int cartItemId);
    
}
