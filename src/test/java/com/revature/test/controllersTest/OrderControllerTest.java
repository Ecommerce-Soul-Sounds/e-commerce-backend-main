// package com.revature.test.controllersTest;

// import static org.assertj.core.api.Assertions.assertThat;

// import java.io.IOException;
// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.MethodOrderer;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestMethodOrder;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.MockMvc;

// import com.fasterxml.jackson.core.JsonParseException;
// import com.fasterxml.jackson.core.JsonParser;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.revature.controllers.OrderController;
// import com.revature.controllers.UserController;
// import com.revature.models.Address;
// import com.revature.models.Cart;
// import com.revature.models.CustomerOrder;
// import com.revature.models.OrderStatus;
// import com.revature.models.User;
// import com.revature.models.Wishlist;
// import com.revature.services.CartItemService;
// import com.revature.services.CartService;
// import com.revature.services.OrderService;
// import com.revature.services.OrderStatusService;
// import com.revature.services.UserService;

// @ExtendWith(SpringExtension.class)
// @WebMvcTest(OrderController.class)
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// public class OrderControllerTest {

// private static CustomerOrder o1,o2;
// private static User u1,u2;
// private static Address a1,a2;
// private static Wishlist w1,w2;
// private static Cart c1,c2;
// private static OrderStatus os1,os2;
// static List<CustomerOrder> dummydb;
// ObjectMapper om = new ObjectMapper();

// @Autowired
// OrderController orderController;

// @Autowired
// private MockMvc mockmvc;


// @MockBean
// private  OrderService oservice;
// @MockBean
// private  OrderStatusService orderStatusService;
// @MockBean
// private  CartService cartService;
// @MockBean
// private  CartItemService cartItemService;
// @MockBean
// private  UserService userService;

// public boolean isValidJSON(final String json) {
// 	boolean valid = false;
// 	try {
// 		final JsonParser parser = new ObjectMapper().getFactory().createParser(json);
// 		while (parser.nextToken() != null) {
// 		}
// 		valid = true;
// 	} catch (JsonParseException jpe) {
// 		jpe.printStackTrace();
// 	} catch (IOException ioe) {
// 		ioe.printStackTrace();
// 	}

// 	return valid;
// }

// @BeforeAll
// static void setUpBeforeClass() throws Exception {
// 	System.out.println("setUpBeforeClass() :: building test objects...");
// 	LocalDate date=LocalDate.parse("2020-01-08");
// 	a1= new Address(1,"#5 17th place","3rd Block","Chicago","Illinois",60619);
// 	w1= new Wishlist(1,date);
// 	c1= new Cart(1,date,4);
// 	os1=new OrderStatus(1,"In-cart");	
// 	u1=new User(1,"jowill@gmail.com","jowill","joel","will",new byte[1],a1,w1,c1);
// 	o1 = new CustomerOrder(1,u1,a1,c1,76.87,date,os1);
// 	dummydb = new ArrayList<CustomerOrder>();
// 	dummydb.add(o1);
// }
// @Test
// @Order(1)
// @DisplayName("1. AppContext Sanity Test")
// public void contextLoads() throws Exception {

// 	assertThat(orderController).isNotNull();
// }
// }