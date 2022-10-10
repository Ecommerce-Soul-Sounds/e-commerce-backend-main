package com.revature.dtosTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.dtos.ProductInfo;

public class ProductInfoTest {
	@Test
    @Order(1)
	@DisplayName("1. Test equals and Hashcode.")
    void testequals() {
		ProductInfo pi1=new ProductInfo(1,5);
	    ProductInfo pi2=new ProductInfo(1,5);
		
		assertEquals(pi2, pi1);
		assertTrue(pi1.hashCode()==pi2.hashCode());
		}
	@Test
    @Order(2)
	@DisplayName("2. Test toString.")
    void testtoString() {
		ProductInfo pi1=new ProductInfo(1,5);
		
		assertEquals("ProductInfo(id=1, quantity=5)", pi1.toString());
		}
	@Test
    @Order(3)
	@DisplayName("3. Test Setters.")
    void testsetter() {
		ProductInfo pi1=new ProductInfo();
				pi1.setId(1);
				pi1.setQuantity(4);
		assertTrue(pi1.getId()==1);
		assertTrue(pi1.getQuantity()==4);
		}
	
}
