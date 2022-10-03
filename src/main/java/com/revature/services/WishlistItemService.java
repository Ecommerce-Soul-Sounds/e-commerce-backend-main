package com.revature.services;

import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.models.WishlistItem;
import com.revature.repositories.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistItemService {

    @Autowired
    private WishlistItemRepository wishlistItemRepo;

    public WishlistItem addWishlistItem(WishlistItem wishlistItem) {
        return wishlistItemRepo.save(wishlistItem);
    }

    public WishlistItem getWishlistItemById(int id) {
        Optional<WishlistItem> optional = wishlistItemRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    public List<WishlistItem> getWishlistItemsByWishlistID(int wishlistID) {
        return wishlistItemRepo.getWishlistItemsByWishlistID(wishlistID);
    }

    public void deleteWishlistItem(WishlistItem wishlistItem) {
        wishlistItemRepo.delete(wishlistItem);
    }
}
