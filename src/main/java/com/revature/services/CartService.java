package com.revature.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Cart;
import com.revature.models.CartItem;
import com.revature.models.Product;
import com.revature.repositories.CartItemRepository;
import com.revature.repositories.CartRepository;
import com.revature.repositories.ProductRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemRepository cartItemRepo;
    @Autowired
    private ProductRepository productRepo;

    public CartService(CartRepository cartRepo, CartItemRepository cartItemRepo, ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
    }

    public Cart create(Cart cart) {
        return cartRepo.save(cart);
    }

    public List<Cart> findAll() {
        return cartRepo.findAll();
    }

    public Cart findById(int id) {
        return cartRepo.getById(id);
    }

    public boolean update(Cart cart) {

        Cart target = cartRepo.getById(cart.getId());
        target.setDateModified(cart.getDateModified());
        target.setTotalQuantity(cart.getTotalQuantity());
        target.setId(cart.getId());
        return cartRepo.save(target) != null;

    }

    public boolean delete(Cart cart) {
        cartRepo.delete(cart);
        return true;
    }

    public List<CartItem> getCartItemsByCartId(int id) {
        return cartItemRepo.getCartItemsByCartId(id);
    }

    public boolean addCartItem(Cart cart, int productId, int quantity) {
        List<CartItem> currentCartItems = cartItemRepo.getCartItemsByCartId(cart.getId());
        if (currentCartItems.isEmpty()) {
            if (saveNewCartItem(cart, productId, quantity))
                return cartRepo.updateCart(LocalDate.now(), cart.getTotalQuantity(), cart.getId()) > 0;
        } else {
            for (CartItem cartItem : currentCartItems) {
                if (cartItem.getProduct().getId() == productId) {
                    int currQuantity = cartItem.getQuantity();
                    cartItem.setQuantity(currQuantity + quantity);

                    if (cartItemRepo.updateItem(cartItem.getQuantity(), cartItem.getId()) > 0) {
                        int currCartQuantity = cart.getTotalQuantity();
                        cart.setTotalQuantity(currCartQuantity - currQuantity + cartItem.getQuantity());

                        return cartRepo.updateCart(LocalDate.now(), cart.getTotalQuantity(), cart.getId()) > 0;
                    }
                }
            }
            if (saveNewCartItem(cart, productId, quantity))
                return cartRepo.updateCart(LocalDate.now(), cart.getTotalQuantity(), cart.getId()) > 0;
        }
        return false;
    }

    public boolean saveNewCartItem(Cart cart, int productId, int quantity) {
        CartItem item = new CartItem();

        Optional<Product> product = productRepo.findById(productId);

        if (product.isPresent()) {
            item.setProduct(product.get());
            item.setQuantity(quantity);
            item.setCart(cart);
            cartItemRepo.save(item);

            int currCartQuantity = cart.getTotalQuantity();
            cart.setTotalQuantity(currCartQuantity + item.getQuantity());

            return true;
        }
        return false;
    }

    public boolean deleteCartItem(Cart cart, int productId) {
        CartItem item = new CartItem();

        Optional<Product> product = productRepo.findById(productId);

        if (product.isPresent()) {
            item.setProduct(product.get());
            item.setCart(cart);
            cartItemRepo.delete(item);
            return true;
        }

        return false;
    }

}
