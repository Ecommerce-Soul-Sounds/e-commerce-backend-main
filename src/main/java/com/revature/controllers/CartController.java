package com.revature.controllers;

import static com.revature.util.ClientMessageUtil.*;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.revature.annotations.Authorized;
import com.revature.dtos.CartDTO;
import com.revature.dtos.ProductDTO;
import com.revature.exceptions.CartErrorException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.Cart;
import com.revature.models.CartItem;
import com.revature.models.ClientMessage;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.services.CartItemService;
import com.revature.services.CartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" }, allowCredentials = "true")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Authorized
    @PostMapping("/create-cart")
    public @ResponseBody ClientMessage createCart(@RequestBody CartDTO cart) {
        Cart persistentCart = new Cart();
        persistentCart.setId(cart.getId());
        persistentCart.setTotalQuantity(cart.getTotalQuantity());
        persistentCart.setDateModified(cart.getDateModified());
       
        int code = cartService.create(persistentCart).getId() ;
       switch (code) {
        case 1:
            return CREATION_SUCCESSFUL;
        case 0:
            return CREATION_FAILED;
        case -1:
            return ENTITY_ALREADY_EXISTS;
        }
        
        return null;
    }

    @Authorized
    @GetMapping("/get-cart")
    public @ResponseBody Cart getCart(HttpServletRequest request) {
        User userLoggedIn = (User) request.getSession().getAttribute("loggedInUser");
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
    @PostMapping("/add-to-cart")
    public @ResponseBody Cart addToCart(@RequestBody ProductDTO product, HttpServletRequest request) {

        Product persistentProduct= new Product();

        persistentProduct.setId(product.getId());
        persistentProduct.setQuantity(product.getQuantity());
        persistentProduct.setPrice(product.getPrice());
        persistentProduct.setCategory(product.getCategory());
        persistentProduct.setBrand(product.getBrand());
        persistentProduct.setDescription(product.getDescription());
        persistentProduct.setImage(product.getImage());
        persistentProduct.setName(product.getName());

        User userLoggedIn = (User) request.getSession().getAttribute("loggedInUser");

        if (userLoggedIn != null) {
            Cart currentUserCart = cartService.findById(userLoggedIn.getCart().getId());
            if (currentUserCart != null) {
                int currentCartQuantity = currentUserCart.getTotalQuantity();
                List<CartItem> cartItemList = cartItemService.getCartItems(currentUserCart);

                boolean contains = false;
                int currQuantity = 0;
                int currentCartItemsCount = cartItemList.size();
                CartItem prevCartItem = new CartItem();
                for (CartItem item : cartItemList) {

                    if (item.getProduct().getId() == product.getId()) {

                        contains = true;
                        currQuantity = item.getQuantity();
                        prevCartItem = item;
                        break;
                    }
                }

                if (contains) {
                    prevCartItem.setCart(currentUserCart);
                    prevCartItem.setQuantity(currQuantity + 1);
                    cartItemService.update(prevCartItem);
                } else {
                    CartItem cartItem = new CartItem();
                    cartItem.setCart(currentUserCart);

                    cartItem.setQuantity(currentCartItemsCount + 1);
                    cartItem.setProduct(persistentProduct);

                    cartItemService.create(cartItem);
                }
                currentUserCart.setDateModified(LocalDate.now());
                currentUserCart.setTotalQuantity(currentCartQuantity + 1);
                cartService.update(currentUserCart);

                return currentUserCart;
            } else {
                Cart cart = new Cart();
                cart.setDateModified(LocalDate.now());
                cart.setTotalQuantity(1);

                Cart newCart = cartService.create(cart);

                if (newCart != null) {
                    CartItem cartItem = new CartItem();
                    cartItem.setCart(newCart);
                    cartItem.setQuantity(1);
                    cartItem.setProduct(persistentProduct);

                    if (cartItemService.create(cartItem) != 0) {
                        newCart.setTotalQuantity(1);
                        return newCart;
                    } else {
                        throw new CartErrorException("Item could not be added to cart. Please try again");
                    }
                } else {
                    throw new CartErrorException("Item could not be added to cart. Please try again");
                }
            }

        } else {
            throw new NotLoggedInException("Not logged in. Please log in first.");
        }
    }

    @Authorized
    @PutMapping("/update-cart")
	public  @ResponseBody  ClientMessage  updateCart(@RequestBody CartDTO cart) {
        Cart persistentCart = new Cart();
        persistentCart.setId(cart.getId());
        persistentCart.setTotalQuantity(cart.getTotalQuantity());
        persistentCart.setDateModified(cart.getDateModified());
        
		return cartService.update(persistentCart)  ? UPDATE_SUCCESSFUL : UPDATE_FAILED;
	}
    
    @Authorized
	@DeleteMapping("/delete-cart")
	public  @ResponseBody ClientMessage deleteCart(@RequestBody  CartDTO cart) {
        Cart persistentCart = new Cart();
        persistentCart.setId(cart.getId());
        persistentCart.setTotalQuantity(cart.getTotalQuantity());
        persistentCart.setDateModified(cart.getDateModified());
		return cartService.delete(persistentCart) ? DELETION_SUCCESSFUL : DELETION_FAILED;
	}

}
