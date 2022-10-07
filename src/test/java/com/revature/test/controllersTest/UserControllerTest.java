package com.revature.test.controllersTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.controllers.UserController;
import com.revature.dtos.UpdateUserRequestInfo;
import com.revature.models.Address;
import com.revature.models.Cart;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.services.UserAddressService;
import com.revature.services.UserService;
import com.revature.util.ClientMessageUtil;

@WebMvcTest(UserController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
	private static User u1;
	private static Address a1;
	private static Wishlist w1;
	private static Cart c1;
	private static UpdateUserRequestInfo usri;
	ObjectMapper om = new ObjectMapper().registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());

	@Autowired
	UserController userController;

	@Autowired
	private MockMvc mockmvc;

	@MockBean
	private  UserService uservice;
	@MockBean
	private  UserAddressService uaService;
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
		u1=new User(1,"jowill@gmail.com","jowill","joel","will",new byte[5],a1,w1,c1);
		MockMultipartFile file = new MockMultipartFile(
	        "file", 
	        "hello.txt", 
	        MediaType.TEXT_PLAIN_VALUE, 
	        "Hello, World!".getBytes()
	      );
		usri= new UpdateUserRequestInfo("jowill@gmail.com","jowill","joel","will", "#5 17th place", "APT 1", "Chicago", "Illinois", 60543,file);
}
	@Test
	@Order(1)
	@DisplayName("1. AppContext Sanity Test")
	void contextLoads() throws Exception {

		assertThat(userController).isNotNull();
	}
	@Test
	@Order(2)
	@DisplayName("2. Update User Details Test")
	void testupdateUserDetails() throws Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("user", u1);
		// id number of this creation should be 3
		u1.setId(3);
		//tell Mockito the behavior that I want this method to act like in the mock environment
		when(uservice.updateUser(u1)).thenReturn(u1.getId());
		
		//act
		RequestBuilder request = MockMvcRequestBuilders.put("/user/update")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(om.writeValueAsString(u1))
				.contentType(MediaType.APPLICATION_JSON).session(session);
		MvcResult result = mockmvc.perform(request).andReturn();
		System.out.println(result.getResponse());
		//assert
		assertEquals(om.writeValueAsString(ClientMessageUtil.UPDATE_SUCCESSFUL),
				result.getResponse().getContentAsString());
	}
	
}
