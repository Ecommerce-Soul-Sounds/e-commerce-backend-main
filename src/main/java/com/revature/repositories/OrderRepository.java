package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.CustomerOrder;
@Transactional
@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Integer>  {
	@Query(value="SELECT * FROM customer_order WHERE order_id=?1", nativeQuery=true)
	CustomerOrder findByOrderID(int orderId);

	@Query(value = "SELECT * FROM customer_order WHERE user_id = ?1", nativeQuery = true)
	List<CustomerOrder> findCustomerOrdersByCustomer(int userId);

	@Query(value = "SELECT * FROM customer_order WHERE user_id = ?1 AND status_id = ?2", nativeQuery = true)
	List<CustomerOrder> findCustomerOrdersByStatus(int userid, int statusId);
	
	@Query(value="UPDATE customer_order SET status_id=?1, WHERE order_id=?2", nativeQuery=true)
	public boolean updatestatus(int orderStatus, int orderId);
}