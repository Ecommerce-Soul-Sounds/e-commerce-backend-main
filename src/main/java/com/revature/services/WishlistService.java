package com.revature.services;

import com.revature.models.Product;
import com.revature.models.Wishlist;
import com.revature.models.WishlistItem;
import com.revature.repositories.ProductRepository;
import com.revature.repositories.WishlistItemRepository;
import com.revature.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepo;

    @Autowired
    private WishlistItemRepository wishlistItemRepo;
    
    @Autowired
    private ProductRepository productRepo;
    
    public Wishlist addWishlist(Wishlist wishlist) {
        return wishlistRepo.save(wishlist);
    }

    public Wishlist getWishlistById(int id) {
        return wishlistRepo.getWishlistById(id);
    }

    public int updateWishlist(Wishlist wishlist) {
        return wishlistRepo.updateWishlist(LocalDate.now(), wishlist.getId());
    }

    public boolean deleteWishlist(Wishlist wishlist) {
        wishlistRepo.delete(wishlist);
        return true;
    }

	public List<WishlistItem> getWishlistItemsByWishlist(Wishlist wishlist) {
		return wishlistItemRepo.getWishlistItemsByWishlistID(wishlist.getId());
	}

	public boolean addWishlistItem(Wishlist wishlist, int productId) {
		WishlistItem item = new WishlistItem();
		
		Optional<Product> product = productRepo.findById(productId);
		
		if (product.isPresent()) {
			List<WishlistItem> currentWishlist = wishlistItemRepo.getWishlistItemsByWishlistID(wishlist.getId());
			for (WishlistItem wItem: currentWishlist) {
				if (wItem.getProduct().getId() == productId) {
					return false;
				}
			}
			item.setProduct(product.get());
			item.setWishlist(wishlist);
			return wishlistItemRepo.save(item) != null;
		}
		
		return false;
	}

	public boolean deleteWishlistItem(Wishlist wishlist, int productId) {
		WishlistItem item = new WishlistItem();
		
		Optional<Product> product = productRepo.findById(productId);
		
		if (product.isPresent()) {
			item.setProduct(product.get());
			item.setWishlist(wishlist);
			wishlistItemRepo.delete(item);
			return true;
		}
		
		return false;
	}
}
