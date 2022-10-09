package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.models.CustomerOrder;
import com.revature.models.User;
import com.revature.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" }, allowCredentials = "true")
public class OrderController {

	@Autowired
	private OrderService orderService;

	// get all orders belonging to currently logged in user. Can be filtered by
	// status depending on provided status param
	@Authorized
	@GetMapping("/all")
	public @ResponseBody List<CustomerOrder> getCustomerOrders(@RequestParam(required = false) String status,
			HttpSession session) {
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
	public @ResponseBody String purchase(HttpSession session) {
		User loggedInUser = (User) session.getAttribute("user");

		boolean result = orderService.placeOrder(loggedInUser);

		if (result) {
			return "Order placed successfully.";
		} else {
			return "Order could not be placed at this time. Please try again.";
		}
	}
}