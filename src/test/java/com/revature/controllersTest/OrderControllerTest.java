package com.revature.controllersTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.MethodOrderer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.*;
import com.revature.controllers.OrderController;
import com.revature.models.Address;
import com.revature.models.Cart;
import com.revature.models.CartItem;
import com.revature.models.CustomerOrder;
import com.revature.models.OrderStatus;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.services.OrderService;
@WebMvcTest(OrderController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private OrderController orderController;
	
	@MockBean
	private OrderService mockOrderService;

	private static ObjectMapper om = new ObjectMapper();

	private static MockHttpSession session;
	
	private static CustomerOrder order1, order2;
	private static User user1;
	private static Address address1;
	private static Wishlist wishlist1;
	private static Cart cart1, cart2;
	private static Product product1, product2, product3;
	private static CartItem item1, item2, item3;
	private static OrderStatus os1, os2;
	private static List<CustomerOrder> dummydb;
	private static LocalDate date;

	public boolean isValidJSON(final String json) {
		boolean valid = false;
		try {
			final JsonParser parser = new ObjectMapper().getFactory().createParser(json);
			while (parser.nextToken() != null) {
			}
			valid = true;
		} catch (JsonParseException jpe) {
			jpe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return valid;
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		System.out.println("setUpBeforeClass() :: building test objects...");
		
		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		om.registerModule(new JavaTimeModule());
		
		date = LocalDate.of(2020,1,8);
		
		address1 = new Address(1, "#5 17th place", "3rd Block", "Chicago", "Illinois", 60619);
		cart1 = new Cart(1, date, 4);
		cart2 = new Cart(2, date, 0);
		
		os1 = new OrderStatus(1, "In-cart");
		os2 = new OrderStatus(1, "Shipped");
		
		user1 = new User(1, "jowill@gmail.com", "jowill", "joel", "will", new byte[1], address1, wishlist1, cart1);
		
		order1 = new CustomerOrder(1, user1, address1, cart1, 76.87, date, os1);
		order2 = new CustomerOrder(2, user1, address1, cart2, 33.33, date, os2);
		
		product1 = new Product(1, 1, 1.00, "cat1", "brand1", "desc1", "image1", "name1");
        product2 = new Product(2, 2, 2.00, "cat2", "brand2", "desc2", "image2", "name2");
		product3 = new Product(3, 3, 3.00, "cat3", "brand3", "desc3", "image3", "name3");
        
		item1 = new CartItem(1, 1, product1, cart1);
		item2 = new CartItem(2, 1, product2, cart1);
		item3 = new CartItem(3, 2, product3, cart1);
		
		dummydb = new ArrayList<CustomerOrder>();
		dummydb.add(order1);
		dummydb.add(order2);
	}

	@Test
	@Order(1)
	@DisplayName("1. AppContext Sanity Test")
	void contextLoads() throws Exception {
		assertThat(orderController).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("2. Test get all orders by customer")
	void testGetCustomerOrders() throws Exception {
		session = new MockHttpSession();
		session.setAttribute("user", user1);
		
		List<CustomerOrder> allOrders = new ArrayList<>();
		allOrders.add(order1);
		allOrders.add(order2);
		
		when(mockOrderService.getAllCustomerOrders(user1)).thenReturn(allOrders);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/orders/all")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON)
				.session(session);
		
		mockmvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(om.writeValueAsString(allOrders)));;
	}

	@Test
	@Order(3)
	@DisplayName("3.Test get orders by customer and status")
	void testPlaceOrder() throws Exception {
		session = new MockHttpSession();
		session.setAttribute("user", user1);
		
		List<CustomerOrder> ordersByStatus = new ArrayList<>();
		ordersByStatus.add(order1);
		
		when(mockOrderService.getCustomerOrdersByStatus(user1, "In-Cart")).thenReturn(ordersByStatus);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/orders/all")
				.param("status", "In-Cart")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON)
				.session(session);
		
		mockmvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(om.writeValueAsString(ordersByStatus)));
	}
	
	@Test
	@Order(4)
	@DisplayName("4.Test Purchase")
	void testPurchase() throws Exception {
		session = new MockHttpSession();
		session.setAttribute("user", user1);
		
		when(mockOrderService.placeOrder(user1)).thenReturn(true);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/orders/purchase")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON)
				.session(session);
		
		mockmvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}