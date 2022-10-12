package com.revature.controllers;

import static com.revature.util.ClientMessageUtil.*;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.revature.dtos.CartItemDTO;
import com.revature.util.ClientMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.revature.annotations.Authorized;
import com.revature.dtos.CartDTO;
import com.revature.exceptions.CartErrorException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.Cart;
import com.revature.models.CartItem;
import com.revature.models.ClientMessage;
import com.revature.models.User;
import com.revature.services.CartService;

@RestController
@RequestMapping("api/cart")
@CrossOrigin(origins = { "http://soulsounds.s3-website-us-west-2.amazonaws.com","http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class CartController {
    @Autowired
    private CartService cartService;

    @Authorized
    @PostMapping("/create-cart")
    public @ResponseBody Cart createCart(@RequestBody CartDTO cart) {
        Cart persistentCart = new Cart();
        persistentCart.setId(cart.getId());
        persistentCart.setTotalQuantity(cart.getTotalQuantity());
        persistentCart.setDateModified(cart.getDateModified());

        return cartService.create(persistentCart);

    }

    @Authorized
    @GetMapping("/get-cart")
    public @ResponseBody Cart getCart(HttpSession session) {
        User userLoggedIn = (User) session.getAttribute("user");

        if (userLoggedIn != null) {
//            Optional<Cart> currentUserCart = cartService.findById(userLoggedIn.getCart().getId());
//
//            if (currentUserCart.isPresent()) {
//                return currentUserCart.get();
//            } else {
//                throw new CartErrorException("Cart Not Found");
//            }
            Cart currentUserCart = cartService.findById(userLoggedIn.getCart().getId());
            if (currentUserCart != null) {
                return currentUserCart;
            } else {
                throw new CartErrorException("Cart Not Found");
            }

        } else {
            throw new NotLoggedInException("Not logged in. Please log in first.");
        }
    }

    @Authorized
    @PutMapping("/update-cart")
    public @ResponseBody ClientMessage updateCart(@RequestBody CartDTO cart) {
        Cart persistentCart = new Cart();
        persistentCart.setId(cart.getId());
        persistentCart.setTotalQuantity(cart.getTotalQuantity());
        persistentCart.setDateModified(cart.getDateModified());

        return cartService.update(persistentCart) ? UPDATE_SUCCESSFUL : UPDATE_FAILED;
    }

    @Authorized
    @DeleteMapping("/delete-cart")
    public @ResponseBody ClientMessage deleteCart(@RequestBody CartDTO cart) {
        Cart persistentCart = new Cart();
        persistentCart.setId(cart.getId());
        persistentCart.setTotalQuantity(cart.getTotalQuantity());
        persistentCart.setDateModified(cart.getDateModified());
        return cartService.delete(persistentCart) ? DELETION_SUCCESSFUL : DELETION_FAILED;
    }

    @Authorized
    @PostMapping("/add-item")
    public @ResponseBody ClientMessage addCartItem(@RequestBody CartItemDTO cartItemDTO, HttpSession session) {
        User userLoggedIn = (User) session.getAttribute("user");
//        cartService.update(userLoggedIn.getCart());
        System.out.println("User Cart" + cartService.findById(userLoggedIn.getCart().getId()));
        boolean success = cartService.addCartItem(userLoggedIn.getCart(), cartItemDTO.getProductId(), cartItemDTO.getQuantity());
        userLoggedIn.setCart(cartService.findById(userLoggedIn.getCart().getId()));
        return success ? ADD_CART_ITEM_SUCCESSFUL : ADD_CART_ITEM_FAILED;
    }

    @Authorized
    @DeleteMapping("/delete-item")
    public @ResponseBody ClientMessage deleteCartItem(@RequestBody CartItemDTO cartItemDTO, HttpSession session) {
        User userLoggedIn = (User) session.getAttribute("user");
        cartService.deleteCartItem(userLoggedIn.getCart(), cartItemDTO.getId(), cartItemDTO.getQuantity());
//        return success ? "Item successfully removed from your Cart."
//                : "Something went wrong, item could not be removed.";
        return DELETION_SUCCESSFUL;
    }

    @Authorized
    @GetMapping("/items")
    public @ResponseBody List<CartItem> getCurrentCartItems(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        return cartService.getCartItemsByCartId(loggedInUser.getCart().getId());
    }

    @Authorized
    @GetMapping("/order-items")
    public @ResponseBody List<CartItem> getPreviousOrderCartItems(@RequestParam int cartId) {
        return cartService.getCartItemsByCartId(cartId);
    }

    @Authorized
    @DeleteMapping("/empty")
    public @ResponseBody ClientMessage emptyCart(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        cartService.emptyCart(loggedInUser.getCart().getId());
        cartService.update(loggedInUser.getCart());

        return DELETION_SUCCESSFUL;
    }
}
