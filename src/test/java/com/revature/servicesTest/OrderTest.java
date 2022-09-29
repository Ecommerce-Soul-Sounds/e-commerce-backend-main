package com.revature.servicesTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.Address;
import com.revature.models.Cart;
import com.revature.models.Order;
import com.revature.models.OrderStatus;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.repositories.OrderRepository;
import com.revature.services.OrderService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderTest {
	@Mock
	private static OrderRepository mockdao;
	@InjectMocks
	private static OrderService oserv;
	private static Order o1,o2;
	private static User u1,u2;
	private static Address a1,a2;
	private static Wishlist w1,w2;
	private static Cart c1,c2;
	private static OrderStatus os1,os2;
	static List<Order> dummydb;
	@BeforeAll
	static void setUpBeforeClass() throws Exception{
		mockdao = Mockito.mock(OrderRepository.class);
		LocalDate date=LocalDate.parse("2020-01-08");
		oserv = new OrderService(mockdao);
		a1= new Address(1,"#5 17th place","3rd Block","Chicago","Illinois",60619);
		w1= new Wishlist(1,date);
		c1= new Cart(1,date,4);
		os1=new OrderStatus(1,"In-cart");	
		u1=new User(1,"jowill@gmail.com","jowill","joel","will",new byte[1],a1,w1,c1);
		o1 = new Order(1,u1,a1,c1,76.87,date,os1);
		dummydb = new ArrayList<Order>();
		dummydb.add(o1);
	}
	@Test
	//@Order(1)
	@DisplayName("1. Mock Validation Sanity Test")
	void checkMockInjection() {
		assertThat(mockdao).isNotNull();
		assertThat(oserv).isNotNull();
}
	@Test
	//@Order(2)
	@DisplayName("2. Create Order Test")
	void testCreateOrder() {
		//arrange step
		LocalDate date=LocalDate.parse("2022-09-28");
		a2= new Address(2,"#11 18th Street","5th Block","Chicago","Illinois",60656);
		w2= new Wishlist(2,date);
		c2= new Cart(2,date,3);
		os2=new OrderStatus(2,"Paid");	
		u2=new User(2,"dota2@gmail.com","dota2","Phantom","assassin",new byte[8],a2,w2,c2);
	     o2 = new Order(2,u2,a2,c2,45.87,date,os2);
		
		//here we will tell mockito what type of behavior to expect from calling certain methods from our dao
        when(mockdao.save(o2)).thenReturn(o2);
        
		//act + assert step
		assertEquals(1, oserv.create(o2));
	}
	@Test
	//@Order(3)
	@DisplayName("3. Get all order list")
	void testGetAllOrderList() {

        when(oserv.findAll()).thenReturn(dummydb);
        assertEquals(dummydb, oserv.findAll());

    }
	@Test
	//@Order(4)
	@DisplayName("4. Get Order By Id")
	void testGetOrderById() {

        when(oserv.findByOrderID(1)).thenReturn(dummydb);
        assertEquals(dummydb, oserv.findByOrderID(1));

    }
	@Test
	//@Order(5)
	@DisplayName("5. Update Order status")
	void testUpdateOrderStatus() {

        o1.setStatus(new OrderStatus(3,"Removed"));

        when(oserv.updatestatus(os1,o1)).thenReturn(true);
		//when(mockdao.save(o1)).thenReturn(o1);
		
		assertEquals(true, oserv.updatestatus(os1,o1));

    }
	 @Test
	//@Order(6)
	@DisplayName("6. Delete order")
		void testDeleteJobListing() {
	        doNothing().when(mockdao).delete(o1);
			//act + assert step
			assertEquals(true, oserv.delete(o1));
	        
		}

}