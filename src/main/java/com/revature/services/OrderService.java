package com.revature.services;

import java.util.List;

import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.CustomerOrder;
import com.revature.models.OrderStatus;
import com.revature.repositories.OrderRepository;
@Service
@Transactional
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}
	public int create(CustomerOrder order) {
		int orderid=orderRepository.save(order).getId();
		return (orderid>0)?1:0;
	}

	public List<CustomerOrder> findAll() {
        return orderRepository.findAll();
    }

	public List<CustomerOrder> getAllCustomerOrders(User customer) {
		return orderRepository.findCustomerOrdersByCustomer(customer.getId());
	}

	public List<CustomerOrder> getCustomerOrdersByStatus(User customer, OrderStatus status) {
		return orderRepository.findCustomerOrdersByStatus(customer.getId(), status.getId());
	}

	public CustomerOrder findByOrderID(int orderId){
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