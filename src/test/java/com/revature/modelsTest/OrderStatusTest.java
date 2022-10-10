package com.revature.modelsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.models.OrderStatus;

public class OrderStatusTest {

    @Test
    @Order(1)
    @DisplayName("1. Test equals and Hashcode.")
    void testequals() {
        OrderStatus os1 = new OrderStatus(1, "In-Cart");
        OrderStatus os2 = new OrderStatus(1, "In-Cart");

        assertEquals(os2, os1);
        assertTrue(os1.hashCode() == os2.hashCode());
    }

    @Test
    @Order(2)
    @DisplayName("2. Test toString.")
    void testtoString() {
        OrderStatus os1 = new OrderStatus(1, "In-Cart");

        assertEquals("OrderStatus(id=1, status=In-Cart)", os1.toString());
    }

    @Test
    @Order(3)
    @DisplayName("2. Test Setters.")
    void testsetter() {
        OrderStatus os1 = new OrderStatus();
        os1.setId(1);
        os1.setStatus("In-cart");
        assertTrue(os1.getId() == 1);
        assertTrue(os1.getStatus() == "In-cart");
    }

}