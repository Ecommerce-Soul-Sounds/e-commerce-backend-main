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

    @Autowired
    private UserService userService;

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
        Optional<Cart> cart = cartRepo.findById(id);
        if (cart.isPresent()) {
            return cart.get();
        } else {
            return null;
        }
//        return cartRepo.findById(id);
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
        cart = cartRepo.findById(cart.getId()).get();

        if (currentCartItems.isEmpty()) {

            if (saveNewCartItem(cart, productId, quantity)) {
                return cartRepo.updateCart(LocalDate.now(), cart.getTotalQuantity(), cart.getId()) > 0;
            }

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
        System.out.println("Cart Quantity: " + cart.getTotalQuantity());
        CartItem item = new CartItem();

        Optional<Product> product = productRepo.findById(productId);

        if (product.isPresent()) {
            item.setProduct(product.get());
            item.setQuantity(quantity);
            item.setCart(cart);
            cartItemRepo.save(item);

            int currCartQuantity = cart.getTotalQuantity();
            cart.setTotalQuantity(currCartQuantity + item.getQuantity());
            cart.setDateModified(LocalDate.now());
            update(cart);
            return true;
        }
        return false;
    }

    public boolean deleteCartItem(Cart cart, int cartItemId, int deleteQuantity) {
//        CartItem item = new CartItem();
//
//        Optional<Product> product = productRepo.findById(productId);
//
//        if (product.isPresent()) {
//            item.setProduct(product.get());
//            item.setCart(cart);
//            cartItemRepo.delete(item);
//            return true;
//        }
//
//        return false;

        Optional<CartItem> cartItem = cartItemRepo.findById(cartItemId);

        if (cartItem.isPresent()) {
            int currentQuantity = cartItem.get().getQuantity();

            System.out.println("Cart Item Quantity: " + currentQuantity);
            System.out.println("Delete Quantity: " + deleteQuantity);
            if (currentQuantity > deleteQuantity) {
                cart.setTotalQuantity(cart.getTotalQuantity() - 1);
                cart.setDateModified(LocalDate.now());
                update(cart);
                cartItemRepo.updateItem(currentQuantity - 1, cartItemId);

            } else {
                cart.setTotalQuantity(cart.getTotalQuantity() - cartItem.get().getQuantity());
                cart.setDateModified(LocalDate.now());
                update(cart);
                cartItemRepo.delete(cartItem.get());
            }

            return true;
        } else {
            return false;
        }
    }

    public void emptyCart(int cartId) {
        List<CartItem> cartItems = cartItemRepo.getCartItemsByCartId(cartId);
        cartItemRepo.deleteAll(cartItems);
        cartRepo.updateCart(cartRepo.findById(cartId).get().getDateModified(), 0, cartId);
        System.out.println("User Cart" + cartRepo.findById(cartId));
        //cartRepo.updateCart(LocalDate.now(), 0, cartId);
    }

}
