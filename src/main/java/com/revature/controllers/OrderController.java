package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.models.Cart;
import com.revature.models.CartItem;
import com.revature.models.CustomerOrder;
import com.revature.models.User;
import com.revature.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class OrderController {

    @Autowired
    private OrderService orderService;
}
