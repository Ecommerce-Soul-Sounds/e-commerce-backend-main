package com.revature.test.controllersTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.OrderController;
import com.revature.models.Address;
import com.revature.models.Cart;
import com.revature.models.CustomerOrder;
import com.revature.models.OrderStatus;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.services.OrderService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerTest {

	@Mock
	private static OrderService mockOrderService;

	@InjectMocks
	private static OrderController orderController;

	private static ObjectMapper om = new ObjectMapper();

	private static CustomerOrder o1, o2;
	private static User u1, u2;
	private static Address a1, a2;
	private static Wishlist w1, w2;
	private static Cart c1, c2;
	private static OrderStatus os1, os2;
	private static List<CustomerOrder> dummydb;

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
		LocalDate date = LocalDate.parse("2020-01-08");
		a1 = new Address(1, "#5 17th place", "3rd Block", "Chicago", "Illinois", 60619);
		w1 = new Wishlist(1, date);
		c1 = new Cart(1, date, 4);
		os1 = new OrderStatus(1, "In-cart");
		u1 = new User(1, "jowill@gmail.com", "jowill", "joel", "will", new byte[1], a1, w1, c1);
		o1 = new CustomerOrder(1, u1, a1, c1, 76.87, date, os1);
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
@DisplayName("2. Get Customer Order By status Test")
void testGetCustomerOrderByStatus() throws Exception {
	MockHttpSession session = new MockHttpSession();
	session.setAttribute("user", u1);
	// id number of this creation should be 3
	o1.setId(3);
	 MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
     paramsMap.add("status", os1.getStatus());
	//tell Mockito the behavior that I want this method to act like in the mock environment
	when(mockOrderService.getCustomerOrdersByStatus(u1,os1)).thenReturn(dummydb);
	
	//act
	 RequestBuilder request = MockMvcRequestBuilders.get("/api/orders/all")
             .accept(MediaType.APPLICATION_JSON_VALUE)
             .content(om.writeValueAsString(os1.getStatus()))
             .contentType(MediaType.APPLICATION_JSON).session(session);
     MvcResult result = mockmvc.perform(request).andReturn();
     assertEquals(om.writeValueAsString(dummydb), result.getResponse().getContentAsString());
}
@Test
@Order(3)
@DisplayName("3. Get all Customer Order Test")
void testGetAllCustomerOrder() throws Exception {
	MockHttpSession session = new MockHttpSession();
	session.setAttribute("user", u1);
	// id number of this creation should be 3
	o1.setId(3);
	 MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
     paramsMap.add("status", os1.getStatus());
	//tell Mockito the behavior that I want this method to act like in the mock environment
	when(mockOrderService.getAllCustomerOrders(u1)).thenReturn(dummydb);
	
	//act
	mockmvc.perform(get("/api/orders/all").params(paramsMap))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.size()").value(dummydb.size()))
    .andDo(print());
}


	@Test
	@Order(3)
	@DisplayName("3.Test place order")
	void testPlaceOrder() {

	}

}