package com.revature.repositories;

import com.revature.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    @Query(value = "SELECT * FROM wishlist WHERE wishlist_id = ?1", nativeQuery = true)
    Wishlist getWishlistById(int id);

    @Modifying
    @Query(value = "UPDATE wishlist SET date_modified = ?1 WHERE wishlist_id = ?2", nativeQuery = true)
    int updateWishlist(LocalDate dateModified, int id);
}
