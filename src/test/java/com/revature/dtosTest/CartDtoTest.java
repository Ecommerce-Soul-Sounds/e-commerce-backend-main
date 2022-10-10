package com.revature.dtosTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.dtos.CartDTO;

public class CartDtoTest {
	@Test
    @Order(1)
	@DisplayName("1. Test equals and Hashcode.")
    void testequals() {
		CartDTO c1=new CartDTO(1,LocalDate.now(),2);
		CartDTO c2=new CartDTO(1,LocalDate.now(),2);
		
		assertEquals(c2, c1);
		assertTrue(c1.hashCode()==c2.hashCode());
		}
	@Test
    @Order(2)
	@DisplayName("2. Test toString.")
    void testtoString() {
		CartDTO c1=new CartDTO(1,LocalDate.now(),2);		
		assertEquals("CartDTO(id=1, dateModified=2022-10-09, totalQuantity=2)", c1.toString());
		}
	@Test
    @Order(3)
	@DisplayName("3. Test Setters.")
    void testsetter() {
		CartDTO c1=new CartDTO();
				c1.setId(1);
				c1.setDateModified(LocalDate.now());
				c1.setTotalQuantity(5);
		assertTrue(c1.getId()==1);
		assertTrue(c1.getTotalQuantity()==5);
		assertEquals(LocalDate.now(),c1.getDateModified());
		}
}
