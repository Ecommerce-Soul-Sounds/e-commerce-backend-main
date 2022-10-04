package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.exceptions.NoProductFoundException;
import com.revature.exceptions.WishlistErrorException;
import com.revature.models.*;
import com.revature.services.ProductService;
import com.revature.services.WishlistItemService;
import com.revature.services.WishlistService;
import com.revature.util.ClientMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private WishlistItemService wishlistItemService;

    @Autowired
    private ProductService productService;

    @Authorized
    @GetMapping
    public @ResponseBody Wishlist getUserWishlist(HttpServletRequest request) {
        User loggedInUser = (User) request.getSession().getAttribute("user");

        return wishlistService.getWishlistById(loggedInUser.getWishlist().getId());
    }

    @Authorized
    @GetMapping("/items")
    public @ResponseBody List<WishlistItem> getWishlistItems(HttpServletRequest request) {
        User loggedInUser = (User) request.getSession().getAttribute("user");

        return wishlistItemService.getWishlistItemsByWishlistID(loggedInUser.getWishlist().getId());
    }

    @Authorized
    @PostMapping("/add-item")
    public @ResponseBody WishlistItem addWishlistItem(@RequestBody int productId, HttpServletRequest request) {
        User loggedInUser = (User) request.getSession().getAttribute("user");

        WishlistItem item = new WishlistItem();
        item.setWishlist(loggedInUser.getWishlist());

        Optional<Product> optional = productService.findById(productId);

        if (optional.isPresent()) {
            item.setProduct(optional.get());
            wishlistService.updateWishlist(loggedInUser.getWishlist());
            return wishlistItemService.addWishlistItem(item);
        } else {
            throw new NoProductFoundException("Product with that ID was not found.");
        }
    }

    @Authorized
    @DeleteMapping("/delete-item")
    public @ResponseBody ClientMessage deleteWishlistItem(@RequestBody int productId, HttpServletRequest request) {
        User loggedInUser = (User) request.getSession().getAttribute("user");

        WishlistItem item = new WishlistItem();
        item.setWishlist(loggedInUser.getWishlist());

        Optional<Product> optional = productService.findById(productId);

        if (optional.isPresent()) {
            item.setProduct(optional.get());
            wishlistItemService.deleteWishlistItem(item);
            wishlistService.updateWishlist(loggedInUser.getWishlist());
            return ClientMessageUtil.WISHLIST_ITEM_DELETION_SUCCESSFUL;
        } else {
            throw new NoProductFoundException("Product with that ID was not found.");
        }
    }
}
