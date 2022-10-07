package com.revature.test.controllersTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.AuthController;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.Address;
import com.revature.models.User;
import com.revature.services.AuthService;

@WebMvcTest(AuthController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class AuthControllerTest {

	@Autowired
	private MockMvc mockmvc;
	
	@Autowired
	private AuthController authController;
	
	@MockBean
	private AuthService mockAuthService;
	
	private static MockHttpSession session;
	
	private static User user1;
	private static ObjectMapper om;
	private static Address address1;
	
	@BeforeAll
	static void setUpBeforeClass() {
		om = new ObjectMapper();
		user1 = new User(1, "carl@gmail.com", "password", "carl", "carlson");
		address1 = new Address(1,"124 Str St.", "1", "Madison", "WI", 53426);
	}
	
	@Test
	@Order(1)
	@DisplayName("1. AppContext Sanity Test")
	void contextLoads() {
		assertThat(authController).isNotNull();
		assertThat(mockmvc).isNotNull();
	}
	
	@Test
	@Order(2)
	@DisplayName("2. Test login user ")
	void testLogin() throws Exception{
		session = new MockHttpSession();
		String email = "carl@gmail.com";
		String password = "password";
		LoginRequest body = new LoginRequest(email, password);
		
		when(mockAuthService.findByCredentials(email, password)).thenReturn(Optional.of(user1));
	
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/orders/all")
													.accept(MediaType.APPLICATION_JSON_VALUE)
													.contentType(MediaType.APPLICATION_JSON)
													.content(om.writeValueAsString(body))
													.session(session);
		
		mockmvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());

		
		
		assertEquals(user1, (User) session.getAttribute("user"));
	}
	
	@Test
	@Order(3)
	@DisplayName("3. Test logout user")
	void testLogout() throws Exception {
		session = new MockHttpSession();
		session.setAttribute("user", user1);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/auth/logout")
				.session(session);
		
		mockmvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
		
		assertTrue(session.isInvalid());
	}
	
	@Test
	@Order(4)
	@DisplayName("4.Test register user")
	void testRegister() throws Exception {
		session = new MockHttpSession();
		String line1 = address1.getLine1();
		String line2 = address1.getLine2();
		String city = address1.getCity();
		String state = address1.getState();
		int zip = address1.getZipcode();
		String email = user1.getEmail();
		String password = user1.getPassword();
		String firstName = user1.getFirstName();
		String lastName = user1.getLastName();
		RegisterRequest body = new RegisterRequest(email, password, firstName, lastName, line1, line2, city, state, zip);
	
		when(mockAuthService.register(new User (0, email, password, firstName, lastName), new Address(0,line1, line2, city, state, zip) ))
			.thenReturn(user1);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/auth/register")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(body))
				.session(session);
		
		mockmvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().json(om.writeValueAsString(user1)));
	}
	
}
