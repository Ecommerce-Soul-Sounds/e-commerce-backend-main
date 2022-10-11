package com.revature.dtosTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.dtos.LoginRequest;

public class LoginRequestTest {
    @Test
    @Order(1)
    @DisplayName("1. Test equals and Hashcode.")
    void testequals() {
        LoginRequest lr1 = new LoginRequest("jowill@gmail.com", "jwill");
        LoginRequest lr2 = new LoginRequest("jowill@gmail.com", "jwill");

        assertEquals(lr2, lr1);
        assertTrue(lr1.hashCode() == lr2.hashCode());
    }

    @Test
    @Order(2)
    @DisplayName("2. Test toString.")
    void testtoString() {
        LoginRequest lr2 = new LoginRequest("jowill@gmail.com", "jwill");

        assertEquals("LoginRequest(email=jowill@gmail.com, password=jwill)", lr2.toString());
    }

    @Test
    @Order(3)
    @DisplayName("3. Test Setters.")
    void testsetter() {
        LoginRequest lr2 = new LoginRequest("jowill@gmail.com", "jwill");
        lr2.setEmail("tech@gmail.com");
        lr2.setPassword("Tech");
        assertTrue(lr2.getEmail() == "tech@gmail.com");
        assertTrue(lr2.getPassword() == "Tech");
    }
}