package com.revature.services;

import com.revature.models.Wishlist;
import com.revature.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepo;

    public Wishlist addWishlist(Wishlist wishlist) {
        return wishlistRepo.save(wishlist);
    }

    public Wishlist getWishlistById(int id) {
        return wishlistRepo.getWishlistById(id);
    }

    public int updateWishlist(Wishlist wishlist) {
        return wishlistRepo.updateWishlist(LocalDate.now(), wishlist.getId());
    }

    public void deleteWishlist(Wishlist wishlist) {
        wishlistRepo.delete(wishlist);
    }
}
