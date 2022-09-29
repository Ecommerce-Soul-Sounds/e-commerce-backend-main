package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.OrderStatus;
import com.revature.repositories.OrderStatusRepository;

@Service
@Transactional
public class OrderStatusService {
	public OrderStatusService(OrderStatusRepository orderstatusRepository) {
		super();
		this.orderstatusRepository = orderstatusRepository;
	}

	@Autowired
	private OrderStatusRepository orderstatusRepository;

	public int create(OrderStatus orderstatus) {
		int orderid = orderstatusRepository.save(orderstatus).getId();
		return (orderid > 0) ? 1 : 0;
	}

	public List<OrderStatus> findAll() {
		return orderstatusRepository.findAll();
	}

	public boolean updatestatus(String Status, int StatusId) {
		return orderstatusRepository.updatestatus(Status, StatusId);
	}

	public boolean delete(OrderStatus orderstatus) {
		orderstatusRepository.delete(orderstatus);
		return true;
	}

}