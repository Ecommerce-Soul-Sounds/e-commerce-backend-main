package com.revature.dtosTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.dtos.RegisterRequest;

public class RegisterRequestTest {
	@Test
    @Order(1)
	@DisplayName("1. Test equals and Hashcode.")
    void testequals() {
		RegisterRequest rr1=new RegisterRequest("jowill@gmail.com","jwill","Jo","Will","line1","Apt1","Chicago","Illinois",60616);
		RegisterRequest rr2=new RegisterRequest("jowill@gmail.com","jwill","Jo","Will","line1","Apt1","Chicago","Illinois",60616);
		
		assertEquals(rr2, rr1);
		assertTrue(rr1.hashCode()==rr2.hashCode());
		}
	@Test
    @Order(2)
	@DisplayName("2. Test toString.")
    void testtoString() {
		RegisterRequest rr1=new RegisterRequest("tech@gmail.com","jwill","Jo","Will","line1","Apt1","Chicago","Illinois",60616);		
		assertEquals("RegisterRequest(email=tech@gmail.com, password=jwill, firstName=Jo, lastName=Will, street_address=line1, apt=Apt1, city=Chicago, state=Illinois, zipcode=60616)", rr1.toString());
		}
	@Test
    @Order(3)
	@DisplayName("3. Test Setters.")
    void testsetter() {
		RegisterRequest rr1=new RegisterRequest();		
				rr1.setApt("Apt1");
				rr1.setCity("City");
				rr1.setEmail("Techno@tech.com");
				rr1.setFirstName("tech");
				rr1.setLastName("Master");
				rr1.setPassword("Goodday");
				rr1.setState("State");
				rr1.setStreet_address("Street Address");
				rr1.setZipcode(3432);
		assertTrue(rr1.getApt()=="Apt1");
		assertTrue(rr1.getCity()=="City");
		assertTrue(rr1.getEmail()=="Techno@tech.com");
		assertTrue(rr1.getFirstName()=="tech");
		assertTrue(rr1.getLastName()=="Master");
		assertTrue(rr1.getPassword()=="Goodday");
		assertTrue(rr1.getState()=="State");
		assertTrue(rr1.getStreet_address()=="Street Address");
		assertTrue(rr1.getZipcode()==3432);

		}
}
