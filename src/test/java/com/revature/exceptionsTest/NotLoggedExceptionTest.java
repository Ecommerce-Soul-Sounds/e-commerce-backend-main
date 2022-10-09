package com.revature.exceptionsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.exceptions.NotLoggedInException;

public class NotLoggedExceptionTest {
    @Test
    @Order(1)
    @DisplayName("1. Test NotLoggedIn exception no args.")
    void testNotLoggedErrorExceptionNoArgs() {
        NotLoggedInException messageUtil = new NotLoggedInException();
        assertEquals(null, messageUtil.getMessage());
    }

    @Test
    @Order(2)
    @DisplayName("2. Test NotLoggedIn exception string arg.")
    void testNotLoggedErrorExceptionStringArgs() {
        String message = "test";
        NotLoggedInException messageUtil = new NotLoggedInException("test");
        assertEquals(message, messageUtil.getMessage());

    }

    @Test
    @Order(3)
    @DisplayName("3. Test NotLoggedIn exception string and thowable args.")
    void testNotLoggedErrorExceptionStringThrowableArgs() {

        String message = "test";
        NotLoggedInException messageUtil = new NotLoggedInException("test", null);
        assertEquals(message, messageUtil.getMessage());

    }

    @Test
    @Order(4)
    @DisplayName("4. Test NotLoggedIn exception thowable arg.")
    void testNotLoggedErrorExceptionThrowableArgs() {

        NotLoggedInException messageUtil = new NotLoggedInException(new IllegalAccessException("test"));
        assertEquals(new IllegalAccessException("test").toString(), messageUtil.getMessage());
    }

    @Test
    @Order(1)
    @DisplayName("5. Test NotLoggedIn exception all args.")
    void testNotLoggedErrorExceptionAllArgs() {
        String message = "message";
        IllegalAccessException cause = new IllegalAccessException("cause");
        NotLoggedInException messageUtil = new NotLoggedInException(message, cause, false, false);
        assertEquals(message, messageUtil.getMessage());

    }

}
