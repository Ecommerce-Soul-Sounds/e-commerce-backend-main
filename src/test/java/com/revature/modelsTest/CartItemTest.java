package com.revature.modelsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.models.Cart;
import com.revature.models.CartItem;
import com.revature.models.Product;

public class CartItemTest {
	@Test
    @Order(1)
	@DisplayName("1. Test equals and Hashcode.")
    void testequals() {
		CartItem ci1=new CartItem(1,3,new Product(1, 3, 457.76, "Piano", "Casio", "PSRE423", "Image", "Casio PsrE423"),new Cart(1,LocalDate.now(),3));
		CartItem ci2=new CartItem(1,3,new Product(1, 3, 457.76, "Piano", "Casio", "PSRE423", "Image", "Casio PsrE423"),new Cart(1,LocalDate.now(),3));
	
		assertEquals(ci2, ci1);
		assertTrue(ci1.hashCode()==ci2.hashCode());
		}

	@Test
    @Order(2)
	@DisplayName("2. Test Setters.")
    void testsetter() {
		CartItem ci1=new CartItem(1,3,new Product(1, 3, 457.76, "Piano", "Casio", "PSRE423", "Image", "Casio PsrE423"),new Cart(1,LocalDate.now(),3));
				ci1.setId(1);
		assertTrue(ci1.getId()==1);
		}
	
}
