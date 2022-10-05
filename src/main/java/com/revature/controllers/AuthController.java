package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.Address;
import com.revature.models.Cart;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.services.AuthService;
import com.revature.services.CartService;
import com.revature.services.UserAddressService;
import com.revature.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    @Autowired
    private CartService cartService;
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private UserAddressService addressService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<User> optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());

        if(!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        session.setAttribute("user", optional.get());

        return ResponseEntity.ok(optional.get());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {

        Address receivedAddress = new Address();
        receivedAddress.setLine1(registerRequest.getStreet_address());
        receivedAddress.setLine2(registerRequest.getApt());
        receivedAddress.setCity(registerRequest.getCity());
        receivedAddress.setState(registerRequest.getState());
        receivedAddress.setZipcode(registerRequest.getZipcode());
        Address persistedAddress = addressService.create(receivedAddress);

        User createdUser = new User();
        createdUser.setEmail(registerRequest.getEmail());
        createdUser.setPassword(registerRequest.getPassword());
        createdUser.setFirstName(registerRequest.getFirstName());
        createdUser.setLastName(registerRequest.getLastName());
        createdUser.setAddress(persistedAddress);
        createdUser.setCart(cartService.create(new Cart()));
        createdUser.setWishlist(wishlistService.addWishlist(new Wishlist()));

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(createdUser));
    }
}
