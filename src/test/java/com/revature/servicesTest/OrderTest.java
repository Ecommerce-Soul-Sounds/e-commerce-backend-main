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
import org.junit.jupiter.api.Order;
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
import com.revature.models.CartItem;
import com.revature.models.CustomerOrder;
import com.revature.models.OrderStatus;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.repositories.CartItemRepository;
import com.revature.repositories.CartRepository;
import com.revature.repositories.OrderRepository;
import com.revature.repositories.OrderStatusRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.OrderService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderTest {
	@Mock
	private static OrderRepository mockorder;
	@Mock
	private static OrderStatusRepository mockorderstatus;
	@Mock
	private static CartRepository mockcart;
	@Mock
	private static CartItemRepository mockcartitem;
	@Mock
	private static UserRepository mockuser;
	@InjectMocks
	private static OrderService oserv;
	private static CustomerOrder o1, o2;
	private static User u1, u2;
	private static Address a1, a2;
	private static Wishlist w1, w2;
	private static Cart c1, c2;
	private static OrderStatus os1, os2;
	private static CartItem ci1;
	private static Product p1;
	static List<CustomerOrder> dummydb;
	static List<CartItem> cartitemdb;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		mockorder = Mockito.mock(OrderRepository.class);
		mockcart = Mockito.mock(CartRepository.class);
		mockcartitem = Mockito.mock(CartItemRepository.class);
		mockuser = Mockito.mock(UserRepository.class);
		mockorderstatus = Mockito.mock(OrderStatusRepository.class);
		LocalDate date = LocalDate.parse("2020-01-08");
		oserv = new OrderService(mockorder, mockcart, mockcartitem, mockuser, mockorderstatus);
		a1 = new Address(1, "#5 17th place", "3rd Block", "Chicago", "Illinois", 60619);
		w1 = new Wishlist(1, date);
		c1 = new Cart(1, date, 4);
		os1 = new OrderStatus(1, "In-cart");
		u1 = new User(1, "jowill@gmail.com", "jowill", "joel", "will", new byte[1], a1, w1, c1);
		o1 = new CustomerOrder(1, u1, a1, c1, 76.87, date, os1);
		p1 = new Product(1, 3, 457.76, "Piano", "Casio", "PSRE423", "Image", "Casio PsrE423");
		ci1 = new CartItem(1, 2, p1, c1);
		dummydb = new ArrayList<CustomerOrder>();
		cartitemdb = new ArrayList<CartItem>();
		cartitemdb.add(ci1);
		dummydb.add(o1);
	}

	@Test
	@Order(1)
	@DisplayName("1. Mock Validation Sanity Test")
	void checkMockInjection() {
		assertThat(mockorder).isNotNull();
		assertThat(mockorderstatus).isNotNull();
		assertThat(mockcart).isNotNull();
		assertThat(mockcartitem).isNotNull();
		assertThat(mockuser).isNotNull();
		assertThat(oserv).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("2. Create Order Test")
	void testCreateOrder() {
		// arrange step
		LocalDate date = LocalDate.parse("2022-09-28");
		a2 = new Address(2, "#11 18th Street", "5th Block", "Chicago", "Illinois", 60656);
		w2 = new Wishlist(2, date);
		c2 = new Cart(2, date, 3);
		os2 = new OrderStatus(2, "Paid");
		u2 = new User(2, "dota2@gmail.com", "dota2", "Phantom", "assassin", new byte[8], a2, w2, c2);
		o2 = new CustomerOrder(2, u2, a2, c2, 45.87, date, os2);

		// here we will tell mockito what type of behavior to expect from calling
		// certain methods from our dao
		when(mockorder.save(o2)).thenReturn(o2);
	}

	@Test
	@Order(3)
	@DisplayName("3. Get all order list")
	void testGetAllOrderList() {

		when(oserv.findAll()).thenReturn(dummydb);
		assertEquals(dummydb, oserv.findAll());

	}

	@Test
	@Order(4)
	@DisplayName("4. Get Order By Id")
	void testGetOrderById() {

		when(oserv.findByOrderID(1)).thenReturn(o1);
		assertEquals(o1, oserv.findByOrderID(1));

	}

	@Test
	@Order(5)
	@DisplayName("5. Update Order status")
	void testUpdateOrderStatus() {

		o1.setStatus(new OrderStatus(3, "Removed"));

		when(oserv.updatestatus(os1, o1)).thenReturn(true);

		assertEquals(true, oserv.updatestatus(os1, o1));

	}

	@Test
	@Order(6)
	@DisplayName("6. Delete order")
	void testDeleteJobListing() {
		doNothing().when(mockorder).delete(o1);
		// act + assert step
		assertEquals(true, oserv.delete(o1));

	}

	@Test
	@Order(7)
	@DisplayName("7. Get all order list")
	void testGetAllCartByCartId() {

		when(oserv.findAllByCart(c1)).thenReturn(cartitemdb);
		assertEquals(cartitemdb, oserv.findAllByCart(c1));

	}

	@Test
	@Order(8)
	@DisplayName("8. Get Order Status By Status Name")
	void testgetOrderStatusByName() {

        when(oserv.getStatusByName("In-Review")).thenReturn(os1);
		//when(mockdao.save(o1)).thenReturn(o1);
		
		assertEquals(os1, oserv.getStatusByName("In-Review"));

    }

	@Test
	@Order(9)
	@DisplayName("9. Create Cart")
	void testCreateCart () {

        when(oserv.createCart(c1)).thenReturn(c1);
		//when(mockdao.save(o1)).thenReturn(o1);
		
		assertEquals(c1, oserv.createCart(c1));

    }

	@Test
	    @Order(10)
	    @DisplayName("10. Update User Cart")
	    void testUpdateUserCart() {

	        when(oserv.updateUserCart(u1)).thenReturn(c1.getId());

	        assertEquals(u1.getCart().getId(), oserv.updateUserCart(u1));
	    }

	@Test
	    @Order(11)
	    @DisplayName("11. Get Customer Order By Status Name")
	    void testGetCustomerOrderByStatusName() {
			when(mockorderstatus.getOrderStatusByStatusName(os1.getStatus())).thenReturn(os1);
	        when(oserv.getCustomerOrdersByStatus(u1,os1.getStatus())).thenReturn(dummydb);

	        assertEquals(dummydb, oserv.getCustomerOrdersByStatus(u1,os1.getStatus()));
	    }

	@Test
	    @Order(12)
	    @DisplayName("12. Get All Customer Order By Customer Id")
	    void testGetAllCustomerOrderByCustomerId() {

	        when(oserv.getAllCustomerOrders(u1)).thenReturn(dummydb);

	        assertEquals(dummydb, oserv.getAllCustomerOrders(u1));
	    }

	@Test
	@Order(13)
	@DisplayName("13. Create Order Fail Test")
	void testFailCreateOrder() {
		// arrange step
		LocalDate date = LocalDate.parse("2022-09-28");
		a2 = new Address(1, "#11 18th Street", "5th Block", "Chicago", "Illinois", 60656);
		w2 = new Wishlist(0, date);
		c2 = new Cart(0, date, 0);
		os2 = new OrderStatus(0, "Empty");
		u2 = new User(0, "dota2@gmail.com", "dota2", "Phantom", "assassin", new byte[8], a2, w2, c2);
		o2 = new CustomerOrder(0, u2, a2, c2, 45.87, date, os2);

		// here we will tell mockito what type of behavior to expect from calling
		// certain methods from our dao
		when(mockorder.save(o2)).thenReturn(o2);

		// act + assert step
		assertEquals(0, oserv.create(o2));
	}

	@Test
	@Order(13)
	@DisplayName("13. Test place order.")
	void testPlaceOrder() {
		CustomerOrder newOrder = new CustomerOrder(0, u1, a1, c1, 457.76, LocalDate.now(), null);
		when(mockcartitem.getCartItemsByCartId(u1.getCart().getId())).thenReturn(cartitemdb);
		when(mockorder.save(newOrder)).thenReturn(newOrder);
		assertEquals(true, oserv.placeOrder(u1));
	}

}