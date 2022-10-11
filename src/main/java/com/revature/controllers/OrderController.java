package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.exceptions.CartErrorException;
import com.revature.models.*;
import com.revature.services.*;
import com.revature.util.ClientMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // get all orders belonging to currently logged in user. Can be filtered by status depending on provided status param
    @Authorized
    @GetMapping("/all")
    public @ResponseBody List<CustomerOrder> getCustomerOrders(@RequestParam(required = false) String status, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (status != null) {
            return orderService.getCustomerOrdersByStatus(loggedInUser, status);
        } else {
            return orderService.getAllCustomerOrders(loggedInUser);
        }
    }


    // purchase all items in the current User's cart
    @Authorized
    @PostMapping("/purchase")
    public @ResponseBody ClientMessage purchase(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");

        List<CartItem> items = orderService.findAllByCart(loggedInUser.getCart());

        if (items.isEmpty()) {
            throw new CartErrorException("Cart is Empty");
        } else {
            double totalPrice = 0;

            for (CartItem cartItem : items) {
                totalPrice += cartItem.getProduct().getPrice();
            }

            CustomerOrder order = new CustomerOrder();
            order.setCustomer(loggedInUser);
            order.setAddress(loggedInUser.getAddress());
            order.setCart(loggedInUser.getCart());
            order.setTotal(totalPrice);

            order.setOrderPlacedDate(LocalDate.now());
            order.setStatus(orderService.getStatusByName("pending"));

            if (orderService.create(order) > 0) {
                // Create and assign a new Cart to the User
                Cart newCart = new Cart();
                newCart.setTotalQuantity(0);
                newCart.setDateModified(LocalDate.now());
                Cart persistedCart = orderService.createCart(newCart);
                loggedInUser.setCart(persistedCart);
                // update new User cart in DB
                if (orderService.updateUserCart(loggedInUser) > 0) {
                    return ClientMessageUtil.ORDER_SUBMISSION_SUCCESSFUL;
                } else {
                    return ClientMessageUtil.ORDER_SUBMISSION_FAILED;
                }

            } else {
                return ClientMessageUtil.ORDER_SUBMISSION_FAILED;
            }
        }

    }
}
