package com.revature.repositories;

import com.revature.models.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Integer> {

    @Query(value = "SELECT * FROM wishlist_item WHERE wishlist_id = ?1", nativeQuery = true)
    List<WishlistItem> getWishlistItemsByWishlistID(int wishlistID);

}
