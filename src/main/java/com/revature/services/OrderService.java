package com.revature.services;

import java.time.LocalDate;
import java.util.List;

import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.exceptions.CartErrorException;
import com.revature.models.Cart;
import com.revature.models.CartItem;
import com.revature.models.CustomerOrder;
import com.revature.models.OrderStatus;
import com.revature.repositories.CartItemRepository;
import com.revature.repositories.CartRepository;
import com.revature.repositories.OrderRepository;
import com.revature.repositories.OrderStatusRepository;
import com.revature.repositories.UserRepository;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;
	private CartRepository cartRepo;
	private CartItemRepository cartItemsRepo;
	private UserRepository userRepo;
	private OrderStatusRepository orderStatusRepo;

	public OrderService(OrderRepository orderRepo, CartRepository cartRepo, CartItemRepository cartItemRepo,
			UserRepository userRepo, OrderStatusRepository orderStatusRepo) {
		super();
		this.orderRepo = orderRepo;
		this.cartRepo = cartRepo;
		this.cartItemsRepo = cartItemRepo;
		this.userRepo = userRepo;
		this.orderStatusRepo = orderStatusRepo;
	}

	public List<CustomerOrder> findAll() {
		return orderRepo.findAll();
	}

	public List<CustomerOrder> getAllCustomerOrders(User customer) {
		return orderRepo.findCustomerOrdersByCustomer(customer.getId());
	}

	public List<CustomerOrder> getCustomerOrdersByStatus(User customer, String statusName) {
		OrderStatus status = orderStatusRepo.getOrderStatusByStatusName(statusName);
		return orderRepo.findCustomerOrdersByStatus(customer.getId(), status.getId());
	}

	public CustomerOrder findByOrderID(int orderId) {
		return orderRepo.findByOrderID(orderId);
	}

	public boolean placeOrder(User loggedInUser) {
		List<CartItem> items = cartItemsRepo.getCartItemsByCartId(loggedInUser.getCart().getId());

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
			order.setStatus(orderStatusRepo.getOrderStatusByStatusName("pending"));

			if (orderRepo.save(order) != null) {
				// Create and assign a new Cart to the User
				Cart newCart = new Cart();
				newCart.setTotalQuantity(0);
				newCart.setDateModified(LocalDate.now());
				Cart persistedCart = cartRepo.save(newCart);
				loggedInUser.setCart(persistedCart);
				userRepo.save(loggedInUser);
				// update new User cart in DB
				
				return true;
			}

			return false;

		}
	}
}