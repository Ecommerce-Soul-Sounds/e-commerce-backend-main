package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.Order;
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
	public int create(Order order) {
		int orderid=orderRepository.save(order).getId();
		return (orderid>0)?1:0;
	}
	public List<Order> findAll() {
        return orderRepository.findAll();
    }
	public List<Order> findByOrderID(int orderId){
		return orderRepository.findByOrderID(orderId);
	}
	public boolean updatestatus(OrderStatus orderStatus, Order OrderId) {
		return orderRepository.updatestatus(orderStatus.getId(),OrderId.getId());
	}
	public boolean delete(Order order) {
		orderRepository.delete(order);
		return true;
	}
	}