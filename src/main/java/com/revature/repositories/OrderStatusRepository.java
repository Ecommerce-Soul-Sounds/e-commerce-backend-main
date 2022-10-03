package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.OrderStatus;

@Transactional
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {

	@Query(value = "SELECT * FROM order_status WHERE status =?1", nativeQuery = true)
	OrderStatus getOrderStatusByStatusName(String status);

	@Query(value="UPDATE order_status SET status=?1, WHERE status_id=?2", nativeQuery=true)
	public boolean updatestatus(String status, int statusId);
}
