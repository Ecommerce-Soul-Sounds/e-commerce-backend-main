package com.revature.exceptionsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.exceptions.CartErrorException;

public class CartErrorExceptionTest {

    @Test
    @Order(1)
	@DisplayName("1. Test cart exception no args.")
    void testCartErrorExceptionNoArgs() {
        CartErrorException messageUtil = new CartErrorException();
        assertEquals(null, messageUtil.getMessage());
    }

    @Test
    @Order(2)
	@DisplayName("2. Test cart exception string arg.")
    void CartErrorExceptionStringArgs() {
        String message = "test";
        CartErrorException messageUtil = new CartErrorException("test");
        assertEquals(message, messageUtil.getMessage());

    }

    @Test
    @Order(3)
	@DisplayName("3. Test cart exception string and thowable args.")
    void CartErrorExceptionStringThrowableArgs() {

        String message = "test";
        CartErrorException messageUtil = new CartErrorException("test", null);
        assertEquals(message, messageUtil.getMessage());

    }

    @Test
    @Order(4)
	@DisplayName("4. Test cart exception thowable arg.")
    void CartErrorExceptionThrowableArgs() {

        CartErrorException messageUtil = new CartErrorException(new IllegalAccessException("test"));
        assertEquals(new IllegalAccessException("test").toString(), messageUtil.getMessage());
    }

    @Test
    @Order(1)
	@DisplayName("5. Test cart exception all args.")
    void CartErrorExceptionAllArgs() {
        String message = "message";
        IllegalAccessException cause = new IllegalAccessException("cause");
        CartErrorException messageUtil = new CartErrorException(message, cause, false, false);
        assertEquals(message, messageUtil.getMessage());

    }

}
