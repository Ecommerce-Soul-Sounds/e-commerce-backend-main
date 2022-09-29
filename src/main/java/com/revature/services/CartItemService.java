package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.CartItem;
import com.revature.repositories.CartItemRepository;

@Service
@Transactional
public class CartItemService {
    
    @Autowired
    private final CartItemRepository cartItemRepository;
   
    public CartItemService(CartItemRepository cartItemRepository){
        super();
        this.cartItemRepository=cartItemRepository;
    }
   


    public int create(CartItem cartItem){
        int id= cartItemRepository.save(cartItem).getId();
        return (id>0)?1:0;
    }

    public  List<CartItem> findAllByCartId(CartItem cartItem){
        return cartItemRepository.getAllByCartId(cartItem.getCart().getId());

    }
    public CartItem findById(int id){
        return cartItemRepository.getById(id);
    }
    public boolean update(CartItem cartItem){
        return cartItemRepository.updateItem(cartItem.getQuantity(), cartItem.getId());
    }
    public boolean delete(CartItem cartItem){
        cartItemRepository.delete(cartItem);
        return true;
    }
    
}
