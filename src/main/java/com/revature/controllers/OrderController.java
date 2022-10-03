package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.models.CustomerOrder;
import com.revature.models.User;
import com.revature.services.OrderService;
import com.revature.services.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStatusService orderStatusService;

    // get all orders belonging to current logged in user. Can be filtered by status depending on provided status param
    @Authorized
    @GetMapping("/all")
    public @ResponseBody List<CustomerOrder> getCustomerOrders(@RequestParam(required = false) String status, HttpServletRequest request) {
        User loggedInUser = (User) request.getSession().getAttribute("user");
        if (status != null) {
            return orderService.getCustomerOrdersByStatus(loggedInUser, orderStatusService.getStatusByName(status));
        } else {
            return orderService.getAllCustomerOrders(loggedInUser);
        }
    }
}
