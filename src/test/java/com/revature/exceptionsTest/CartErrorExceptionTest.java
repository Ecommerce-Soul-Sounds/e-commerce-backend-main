package com.revature.exceptionsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.revature.exceptions.CartErrorException;

public class CartErrorExceptionTest {

    @Test
    void testCartErrorExceptionNoArgs() {
        //String message = "null";	
        CartErrorException messageUtil = new CartErrorException();
        assertEquals(null,messageUtil.getMessage());
    }

    @Test
    void CartErrorExceptionStringArgs() {
        String message = "test";	
        CartErrorException messageUtil = new CartErrorException("test");
        assertEquals(message,messageUtil.getMessage());
        
    }

    @Test
    void CartErrorExceptionStringThrowableArgs() {
        
        String message = "test";	
        CartErrorException messageUtil = new CartErrorException("test",null);
        assertEquals(message,messageUtil.getMessage());
        
    }

    @Test
    void CartErrorExceptionThrowableArgs() {
       
        CartErrorException messageUtil = new CartErrorException(new IllegalAccessException("test"));
        assertEquals(new IllegalAccessException("test").toString(),messageUtil.getMessage());
    }

    @Test
    void CartErrorExceptionAllArgs() {
        String message = "message";
        IllegalAccessException cause= new IllegalAccessException("casue");
        CartErrorException messageUtil = new CartErrorException(message, cause, false, false);
        assertEquals(message,messageUtil.getMessage());
        
    }
    
}
