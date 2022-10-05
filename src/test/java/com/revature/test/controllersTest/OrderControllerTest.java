package com.revature.test.controllersTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.controllers.OrderController;
import com.revature.models.Address;
import com.revature.models.Cart;
import com.revature.models.CustomerOrder;
import com.revature.models.OrderStatus;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.services.CartService;
import com.revature.services.OrderService;
import com.revature.services.OrderStatusService;
import com.revature.services.UserService;
import com.revature.util.ClientMessageUtil;

@WebMvcTest(OrderController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerTest {

private static CustomerOrder o1;
private static User u1;
private static Address a1;
private static Wishlist w1;
private static Cart c1;
private static OrderStatus os1;
static List<CustomerOrder> dummydb;
ObjectMapper om = new ObjectMapper().registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());

@Autowired
OrderController orderController;

@Autowired
private MockMvc mockmvc;


@MockBean
private  OrderService oservice;
@MockBean
private  OrderStatusService orderStatusService;
@MockBean
private  CartService cartService;
@MockBean
private  UserService userService;

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
	LocalDate date=LocalDate.parse("2022-10-04");
	a1= new Address(1,"#5 17th place","3rd Block","Chicago","Illinois",60619);
	w1= new Wishlist(1,date);
	c1= new Cart(1,date,4);
	os1=new OrderStatus(1,"In-cart");	
	u1=new User(1,"jowill@gmail.com","jowill","joel","will",new byte[1],a1,w1,c1);
	o1 = new CustomerOrder(1,u1,a1,c1,76.87,date,os1);
	dummydb = new ArrayList<CustomerOrder>();
	dummydb.add(o1);
}
@Test
@Order(1)
@DisplayName("1. AppContext Sanity Test")
void contextLoads() throws Exception {

	assertThat(orderController).isNotNull();
}
@Test
@Order(2)
@DisplayName("2. Get Customer Order Test")
void testCreateUserRole() throws Exception {
	// id number of this creation should be 3
	o1.setId(3);
	//tell Mockito the behavior that I want this method to act like in the mock environment
	when(oservice.create(o1)).thenReturn(1);
	
	//act
	RequestBuilder request = MockMvcRequestBuilders.get("/api/orders/all")
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.content(om.writeValueAsString(o1.getStatus().getStatus()))
			.contentType(MediaType.APPLICATION_JSON);
	MvcResult result = mockmvc.perform(request).andReturn();
	System.out.println(result.getResponse());
	//assert
	assertEquals(om.writeValueAsString(ClientMessageUtil.CREATION_SUCCESSFUL),
			result.getResponse().getContentAsString());
}
}