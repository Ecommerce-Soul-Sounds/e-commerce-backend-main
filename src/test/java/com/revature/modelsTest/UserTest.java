package com.revature.modelsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.models.Address;
import com.revature.models.Cart;
import com.revature.models.User;
import com.revature.models.Wishlist;

public class UserTest {
	@Test
    @Order(1)
	@DisplayName("1. Test equals and Hashcode.")
    void testequals() {
		Address a1=new Address(1, "#5 17th place", "3rd Block", "Chicago", "Illinois", 60619);
		Wishlist w1=new Wishlist(1, LocalDate.now());
		Cart c1=new Cart(1, LocalDate.now(), 4);
		User u1=new User(1, "jowill@gmail.com", "jowill", "joel", "will", new byte[1], a1, w1, c1);
		User u2=new User(1, "jowill@gmail.com", "jowill", "joel", "will", new byte[1], a1, w1, c1);
		
		
		assertEquals(u2, u1);
		assertTrue(u1.hashCode()==u2.hashCode());
		}
}
