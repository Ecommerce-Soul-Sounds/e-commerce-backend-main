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
	private OrderRepository orderRepository;
	private CartRepository cartRepository;
	private CartItemRepository cartitemrepository;
	private UserRepository userrepository;

	public OrderService(OrderRepository orderRepository, CartRepository cartRepository,
			CartItemRepository cartitemrepository, UserRepository userrepository,
			OrderStatusRepository orderStatusRepository) {
		super();
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.cartitemrepository = cartitemrepository;
		this.userrepository = userrepository;
		this.orderStatusRepository = orderStatusRepository;
	}

	private OrderStatusRepository orderStatusRepository;

	public int create(CustomerOrder order) {
		int orderid = orderRepository.save(order).getId();
		return (orderid > 0) ? 1 : 0;
	}

	public List<CustomerOrder> findAll() {
		return orderRepository.findAll();
	}

	public List<CustomerOrder> getAllCustomerOrders(User customer) {
		return orderRepository.findCustomerOrdersByCustomer(customer.getId());
	}

	public List<CartItem> findAllByCart(Cart cart) {
		return cartitemrepository.getCartItemsByCartId(cart.getId());

	}

	public OrderStatus getStatusByName(String status) {
		return orderStatusRepository.getOrderStatusByStatusName(status);
	}

	public Cart createCart(Cart cart) {
		return cartRepository.save(cart);
	}

	public int updateUserCart(User user) {
		return userrepository.updateUserCart(user.getCart().getId(), user.getId());
	}

	public List<CustomerOrder> getCustomerOrdersByStatus(User customer, String statusName) {
		OrderStatus status = orderStatusRepository.getOrderStatusByStatusName(statusName);
		return orderRepository.findCustomerOrdersByStatus(customer.getId(), status.getId());
	}

	public CustomerOrder findByOrderID(int orderId) {
		return orderRepository.findByOrderID(orderId);
	}

	public boolean updatestatus(OrderStatus orderStatus, CustomerOrder orderId) {
		return orderRepository.updatestatus(orderStatus.getId(), orderId.getId());
	}

	public boolean delete(CustomerOrder order) {
		orderRepository.delete(order);
		return true;
	}

	public boolean placeOrder(User loggedInUser) {
		List<CartItem> items = cartitemrepository.getCartItemsByCartId(loggedInUser.getCart().getId());

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
			order.setStatus(orderStatusRepository.getOrderStatusByStatusName("pending"));

			if (orderRepository.save(order) != null) {
				// Create and assign a new Cart to the User
				Cart newCart = new Cart();
				newCart.setTotalQuantity(0);
				newCart.setDateModified(LocalDate.now());
				Cart persistedCart = cartRepository.save(newCart);
				loggedInUser.setCart(persistedCart);
				userrepository.save(loggedInUser);
				// update new User cart in DB

				return true;
			}

			return false;

		}
	}
}