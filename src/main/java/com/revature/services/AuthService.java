package com.revature.services;

import com.revature.models.Address;
import com.revature.models.Cart;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.repositories.CartRepository;
import com.revature.repositories.UserAddressRepository;
import com.revature.repositories.WishlistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private WishlistRepository wishlistRepo;

    @Autowired
    private UserAddressRepository addressRepo;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userService.findByCredentials(email, password);
    }

    public User register(User user, Address address) {
        Address persistedAddress = addressRepo.save(address);
        Cart cart = cartRepo.save(new Cart());
        Wishlist wishlist = wishlistRepo.save(new Wishlist());

        if (persistedAddress == null || cart == null || wishlist == null) {
            return null;
        }

        user.setAddress(persistedAddress);
        user.setCart(cart);
        user.setWishlist(wishlist);
        return userService.save(user);
    }
}
