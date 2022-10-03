package com.revature.services;

import java.util.List;

import com.revature.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.CartItem;
import com.revature.repositories.CartItemRepository;

@Service
@Transactional
public class CartItemService {
    
    @Autowired
    private CartItemRepository cartItemRepository;
   
    public CartItemService(CartItemRepository cartItemRepository){
        super();
        this.cartItemRepository=cartItemRepository;
    }
   


    public int create(CartItem cartItem){
        int id= cartItemRepository.save(cartItem).getId();
        return (id>0)?1:0;
    }

    public  List<CartItem> findAllByCart(Cart cart){
        return cartItemRepository.getAllByCartId(cart.getId());

    }

	public List<CartItem> getCartItems(Cart cart) {
		return cartItemRepository.getAllByCartId(cart.getId());
	}

    public CartItem findById(int id){
        return cartItemRepository.getById(id);
    }
    public boolean update(CartItem cartItem){
        CartItem target = cartItemRepository.getById(cartItem.getId());
		target.setQuantity(cartItem.getQuantity());
        target.setProduct(cartItem.getProduct());
		target.setId(cartItem.getId());
		return cartItemRepository.save(target) != null;
    }
    public boolean delete(CartItem cartItem){
        cartItemRepository.delete(cartItem);
        return true;
    }
    
}