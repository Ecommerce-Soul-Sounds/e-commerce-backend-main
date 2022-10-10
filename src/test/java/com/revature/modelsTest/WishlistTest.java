package com.revature.modelsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.models.Wishlist;

public class WishlistTest {
	@Test
    @Order(1)
	@DisplayName("1. Test Setters.")
    void testsetter() {
		Wishlist wl1=new Wishlist();
				wl1.setId(1);
				wl1.setDateModified(LocalDate.now());
		assertTrue(wl1.getId()==1);
		assertEquals(LocalDate.now(),wl1.getDateModified());
		}
	@Test
    @Order(2)
	@DisplayName("2. Test equals and Hashcode.")
    void testequals() {
		Wishlist wl1=new Wishlist(1,LocalDate.now());
		Wishlist wl2=new Wishlist(1,LocalDate.now());
		
		assertEquals(wl2, wl1);
		assertTrue(wl1.hashCode()==wl2.hashCode());
		}
}
