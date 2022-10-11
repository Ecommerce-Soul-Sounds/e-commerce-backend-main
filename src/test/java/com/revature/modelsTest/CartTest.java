package com.revature.modelsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.models.Cart;

public class CartTest {
    @Test
    @Order(1)
    @DisplayName("1. Test equals and Hashcode.")
    void testequals() {
        Cart c1 = new Cart(1, LocalDate.now(), 3);
        Cart c2 = new Cart(1, LocalDate.now(), 3);

        assertEquals(c2, c1);
        assertTrue(c1.hashCode() == c2.hashCode());
    }
}