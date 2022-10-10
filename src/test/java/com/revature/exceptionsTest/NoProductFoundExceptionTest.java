package com.revature.exceptionsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.revature.exceptions.NoProductFoundException;

public class NoProductFoundExceptionTest {
    @Test
    @DisplayName("2. Test no product found exception.")
    void testErrorException() {
        String message = "test";
        NoProductFoundException messageUtil = new NoProductFoundException("test");
        assertEquals(message, messageUtil.getMessage());

    }

}
