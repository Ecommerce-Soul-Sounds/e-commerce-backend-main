package com.revature.modelsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.models.Address;

public class AddressTest {
	@Test
    @Order(1)
	@DisplayName("1. Test equals and Hashcode.")
    void testequals() {
		Address a1=new Address(1, "#5 17th place", "3rd Block", "Chicago", "Illinois", 60619);
		Address a2=new Address(1, "#5 17th place", "3rd Block", "Chicago", "Illinois", 60619);
		
		
		assertEquals(a2, a1);
		assertTrue(a1.hashCode()==a2.hashCode());
		}
}
