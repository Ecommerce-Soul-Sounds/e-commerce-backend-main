package com.revature.modelsTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.models.ClientMessage;

public class ClientMessageTest {
    @Test
    @Order(1)
    @DisplayName("1. Test Setters.")
    void testsetter() {
        ClientMessage cm1 = new ClientMessage();
        cm1.setMessage("Not-Responding");
        assertTrue(cm1.getMessage() == "Not-Responding");
    }
}