package com.revature.modelsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.models.Product;
import com.revature.models.Wishlist;
import com.revature.models.WishlistItem;

public class WishlistitemTest {
    @Test
    @Order(1)
    @DisplayName("1. Test equals and Hashcode.")
    void testequals() {
        WishlistItem wli1 = new WishlistItem(1,
                new Product(1, 3, 457.76, "Piano", "Casio", "PSRE423", "Image", "Casio PsrE423"),
                new Wishlist(1, LocalDate.now()));
        WishlistItem wli2 = new WishlistItem(1,
                new Product(1, 3, 457.76, "Piano", "Casio", "PSRE423", "Image", "Casio PsrE423"),
                new Wishlist(1, LocalDate.now()));

        assertEquals(wli2, wli1);
        assertTrue(wli1.hashCode() == wli2.hashCode());
    }

    @Test
    @Order(2)
    @DisplayName("2. Test toString.")
    void testtoString() {
        WishlistItem wli1 = new WishlistItem(1,
                new Product(1, 3, 457.76, "Piano", "Casio", "PSRE423", "Image", "Casio PsrE423"),
                new Wishlist(1, LocalDate.now()));

        assertEquals(
                "WishlistItem(id=1, product=Product(id=1, quantity=3, price=457.76, category=Piano, brand=Casio, description=PSRE423, image=Image, name=Casio PsrE423), wishlist=Wishlist(id=1, dateModified=2022-10-10))",
                wli1.toString());
    }

    @Test
    @Order(3)
    @DisplayName("2. Test Setters.")
    void testsetter() {
        WishlistItem wli1 = new WishlistItem();
        wli1.setId(1);
        assertTrue(wli1.getId() == 1);
    }

}