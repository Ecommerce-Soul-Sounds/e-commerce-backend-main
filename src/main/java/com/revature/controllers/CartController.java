package com.revature.controllers;

import static com.revature.util.ClientMessageUtil.*;

import java.util.List;

import javax.servlet.http.HttpSession;

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
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" }, allowCredentials = "true")
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
    public @ResponseBody String addCartItem(@RequestBody int productId, HttpSession session) {
        User userLoggedIn = (User) session.getAttribute("user");
        cartService.update(userLoggedIn.getCart());
        boolean success = cartService.addCartItem(userLoggedIn.getCart(), productId);
        return success ? "Item added to wishlist" : "Something went wrong, item was not added to wishlist.";
    }

    @Authorized
    @DeleteMapping("/delete-item")
    public @ResponseBody String deleteWishlistItem(@RequestBody int productId, HttpSession session) {
        User userLoggedIn = (User) session.getAttribute("user");
        boolean success = cartService.deleteCartItem(userLoggedIn.getCart(), productId);
        return success ? "Item successfully removed from your wishlist."
                : "Something went wrong, item could not be removed.";
    }

    @Authorized
    @GetMapping("/items")
    public @ResponseBody List<CartItem> getWishlistItems(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        return cartService.getCartItemsByCartId(loggedInUser.getCart().getId());
    }

}
