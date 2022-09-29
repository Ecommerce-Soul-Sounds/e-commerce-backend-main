package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.OrderStatus;

@Transactional
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
	@Query(value="UPDATE orderstatus SET status=?1, WHERE status_id=?2", nativeQuery=true)
	public boolean updatestatus(String status, int StatusId);
}
