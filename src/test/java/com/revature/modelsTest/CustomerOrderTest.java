package com.revature.modelsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.models.Address;
import com.revature.models.Cart;
import com.revature.models.CustomerOrder;
import com.revature.models.OrderStatus;
import com.revature.models.User;
import com.revature.models.Wishlist;

public class CustomerOrderTest {

	@Test
    @Order(1)
	@DisplayName("1. Test equals and Hashcode.")
    void testequals() {
		Address a1=new Address(1, "#5 17th place", "3rd Block", "Chicago", "Illinois", 60619);
		Wishlist w1=new Wishlist(1, LocalDate.now());
		Cart c1=new Cart(1, LocalDate.now(), 4);
		User u1=new User(1, "jowill@gmail.com", "jowill", "joel", "will", new byte[1], a1, w1, c1);
		OrderStatus os1=new OrderStatus(1, "In-cart");
		CustomerOrder co1=new CustomerOrder(1, u1, a1, c1, 76.87, LocalDate.now(), os1);
		CustomerOrder co2=new CustomerOrder(1, u1, a1, c1, 76.87, LocalDate.now(), os1);
		assertEquals(co2, co1);
		assertTrue(co1.hashCode()==co2.hashCode());
		}
	@Test
    @Order(2)
	@DisplayName("2. Test toString.")
    void testtoString() {
		Address a1=new Address(1, "#5 17th place", "3rd Block", "Chicago", "Illinois", 60619);
		Wishlist w1=new Wishlist(1, LocalDate.now());
		Cart c1=new Cart(1, LocalDate.now(), 4);
		User u1=new User(1, "jowill@gmail.com", "jowill", "joel", "will", new byte[1], a1, w1, c1);
		OrderStatus os1=new OrderStatus(1, "In-cart");
		CustomerOrder co1=new CustomerOrder(1, u1, a1, c1, 76.87, LocalDate.now(), os1);
		
		assertEquals("CustomerOrder(id=1, customer=User(id=1, email=jowill@gmail.com, password=jowill, firstName=joel, lastName=will, picture=[0], address=Address(id=1, line1=#5 17th place, line2=3rd Block, city=Chicago, state=Illinois, zipcode=60619), wishlist=Wishlist(id=1, dateModified=2022-10-09), cart=Cart(id=1, dateModified=2022-10-09, totalQuantity=4)), address=Address(id=1, line1=#5 17th place, line2=3rd Block, city=Chicago, state=Illinois, zipcode=60619), cart=Cart(id=1, dateModified=2022-10-09, totalQuantity=4), total=76.87, orderPlacedDate=2022-10-09, status=OrderStatus(id=1, status=In-cart))", co1.toString());
		}
	@Test
    @Order(3)
	@DisplayName("3. Test Setters.")
    void testsetter() {
		Address a1=new Address(1, "#5 17th place", "3rd Block", "Chicago", "Illinois", 60619);
		Wishlist w1=new Wishlist(1, LocalDate.now());
		Cart c1=new Cart(1, LocalDate.now(), 4);
		User u1=new User(1, "jowill@gmail.com", "jowill", "joel", "will", new byte[1], a1, w1, c1);
		CustomerOrder co1=new CustomerOrder();
				co1.setCustomer(u1);
				co1.setAddress(a1);
				co1.setCart(c1);
				co1.setTotal(768.876);
				co1.setOrderPlacedDate(LocalDate.now());
             assertEquals(u1,co1.getCustomer());
             assertEquals(a1,co1.getAddress());
             assertEquals(c1,co1.getCart());
             assertEquals(768.876,co1.getTotal());
             assertEquals(LocalDate.now(),co1.getOrderPlacedDate());
		}
}
