package com.revature.repositories;

import com.revature.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query(value = "SELECT * FROM cart_item WHERE cart_item_id = ?1", nativeQuery = true)
    CartItem getCartItem(int id);

    @Query(value = "SELECT * FROM cart_item WHERE cart_id = ?1", nativeQuery = true)
    List<CartItem> getCartItemsByCartId(int cartId);

    @Modifying
    @Query(value = "UPDATE cart_item SET quantity = ?1 WHERE cart_item_id = ?2", nativeQuery = true)
    int updateItem(int quantity, int cartItemId);

}
