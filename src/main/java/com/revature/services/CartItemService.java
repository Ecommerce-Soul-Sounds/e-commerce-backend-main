package com.revature.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.models.CartItem;
import com.revature.repositories.CartItemRepository;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepo;

    public CartItemService(CartItemRepository cartItemRepo){
        this.cartItemRepo=cartItemRepo;
    }

    public CartItem create(CartItem cartItem){
        return cartItemRepo.save(cartItem);
    }

    public  List<CartItem> findAllByCartId(CartItem cartItem){
        return cartItemRepo.getAllByCartId(cartItem.getCart().getId());

    }
    public CartItem findById(int id){
        return cartItemRepo.getById(id);
    }
    public boolean update(CartItem cartItem){
        return cartItemRepo.updateItem(cartItem.getQuantity(), cartItem.getId());
    }
    public boolean delete(CartItem cartItem){
        cartItemRepo.delete(cartItem);
        return true;
    }
    
}
