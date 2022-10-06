package com.revature.servicesTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.OrderStatus;
import com.revature.repositories.OrderStatusRepository;
import com.revature.services.OrderStatusService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderStatusTest {
	@Mock
	private static OrderStatusRepository mockdao;
	@InjectMocks
	private static OrderStatusService osserv;
	private static OrderStatus os1,os2;
	static List<OrderStatus> dummydb;
	@BeforeAll
	static void setUpBeforeClass() throws Exception{
		mockdao = Mockito.mock(OrderStatusRepository.class);
		osserv= new OrderStatusService(mockdao);
		os1=new OrderStatus(1,"In-Review");
		dummydb = new ArrayList<OrderStatus>();
		dummydb.add(os1);
	}
	@Test
	@Order(1)
	@DisplayName("1. Mock Validation Sanity Test")
	void checkMockInjection() {
		assertThat(mockdao).isNotNull();
		assertThat(osserv).isNotNull();
	}
	@Test
	@Order(2)
	@DisplayName("2. Create OrderStatus Test")
	void testCreateOrderStatus() {
		//arrange step
	     os2 = new OrderStatus(2,"Paid");
		
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
        when(mockdao.save(os2)).thenReturn(os2);
        
		//act + assert step
		assertEquals(1, osserv.create(os2));
	}
	@Test
	@Order(3)
	@DisplayName("3. Get all orderStatus")
	void testGetAllOrderList() {

        when(osserv.findAll()).thenReturn(dummydb);
        assertEquals(dummydb, osserv.findAll());

    }
	@Test
	@Order(4)
	@DisplayName("4. Update Orderstatus")
	void testUpdateOrderStatus() {

//        os1.setStatus("Denied");
//        os1.setId(3);

        when(osserv.updatestatus("Denied",3)).thenReturn(true);
		//when(mockdao.save(o1)).thenReturn(o1);
		
		assertEquals(true, osserv.updatestatus("Denied",3));

    }
	@Test
	@Order(5)
	@DisplayName("5. Get Order Status By Status Name")
	void testgetOrderStatusByName() {

        when(osserv.getStatusByName("In-Review")).thenReturn(os1);
		//when(mockdao.save(o1)).thenReturn(o1);
		
		assertEquals(os1, osserv.getStatusByName("In-Review"));

    }
	
	@Test
	@Order(6)
	@DisplayName("6. Delete OrderStatus")
		void testDeleteOrderStatus() {
	        doNothing().when(mockdao).delete(os1);
			//act + assert step
			assertEquals(true, osserv.delete(os1));
	        
		}
}
