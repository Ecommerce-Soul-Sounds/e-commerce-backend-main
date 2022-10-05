package com.revature.services;

import java.util.List;

import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		return cartitemrepository.getAllByCartId(cart.getId());

	}


	public OrderStatus getStatusByName(String status) {
		return orderStatusRepository.getOrderStatusByStatusName(status);
	}

	public Cart createcart(Cart cart) {
		return cartRepository.save(cart);
	}

	public int updateUserCart(User user) {
		return userrepository.updateUserCart(user.getId(), user.getCart().getId());
	}

	public List<CustomerOrder> getCustomerOrdersByStatus(User customer, OrderStatus status) {
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
}