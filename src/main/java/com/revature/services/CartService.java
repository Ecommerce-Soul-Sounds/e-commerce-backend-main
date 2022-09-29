package com.revature.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;


import com.revature.models.Cart;
import com.revature.repositories.CartRepository;

@Service
public class CartService {
    private final CartRepository cartRepo;

    public CartService(CartRepository cartRepo){
        this.cartRepo=cartRepo;
    }

    public Cart create(Cart cart){
        return cartRepo.save(cart);
    }

    public List<Cart> findAll() {
        return cartRepo.findAll();
    }

    public Cart findById(int id){
        return cartRepo.getById(id);
    }

    public boolean update(Cart cart){
        return cartRepo.updateCart(LocalDate.now(), cart.getTotalQuantity(), cart.getId());

    }
    public boolean delete(Cart cart){
        cartRepo.delete(cart);
        return true; 
    }

}
