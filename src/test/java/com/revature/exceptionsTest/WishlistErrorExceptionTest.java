package com.revature.exceptionsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.revature.exceptions.WishlistErrorException;

public class WishlistErrorExceptionTest {
    @Test
	@DisplayName("2. Test no product found Exception.")
    void testErrorException() {
        String message = "test";
        WishlistErrorException messageUtil = new WishlistErrorException("test");
        assertEquals(message, messageUtil.getMessage());

    }
    
}
