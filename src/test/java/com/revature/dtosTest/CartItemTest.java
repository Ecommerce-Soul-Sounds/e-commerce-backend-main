package com.revature.dtosTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.dtos.CartItemDTO;

public class CartItemTest {
	@Test
    @Order(1)
	@DisplayName("1. Test equals and Hashcode.")
    void testequals() {
		CartItemDTO ci1=new CartItemDTO(1,1,5);
	    CartItemDTO ci2=new CartItemDTO(1,1,5);
		
		assertEquals(ci2, ci1);
		assertTrue(ci1.hashCode()==ci2.hashCode());
		}
	@Test
    @Order(2)
	@DisplayName("2. Test toString.")
    void testtoString() {
	   CartItemDTO pi1=new CartItemDTO(3,1,5);
		
		assertEquals("CartItemDTO(id=3, productId=1, quantity=5)", pi1.toString());
		}
	@Test
    @Order(3)
	@DisplayName("3. Test Setters.")
    void testsetter() {
		CartItemDTO ci1=new CartItemDTO();
				ci1.setProductId(1);
				ci1.setQuantity(4);
		assertTrue(ci1.getProductId()==1);
		assertTrue(ci1.getQuantity()==4);
		}
	
}