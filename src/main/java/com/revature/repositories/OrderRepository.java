package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.CustomerOrder;
@Transactional
public interface OrderRepository extends JpaRepository<CustomerOrder, Integer>  {
	@Query(value="SELECT * FROM order WHERE order_id=?1", nativeQuery=true)
	public List<CustomerOrder> findByOrderID(int orderId);
	
	@Query(value="UPDATE order SET status_id=?1, WHERE order_id=?2", nativeQuery=true)
	public boolean updatestatus(int OrderStatus, int OrderId);
}