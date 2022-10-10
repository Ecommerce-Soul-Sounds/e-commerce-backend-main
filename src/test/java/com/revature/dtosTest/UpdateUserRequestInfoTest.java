package com.revature.dtosTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.revature.dtos.ProductInfo;
import com.revature.dtos.UpdateUserRequestInfo;

public class UpdateUserRequestInfoTest {
    @Test
    @Order(1)
    @DisplayName("1. Test equals and Hashcode.")
    void testequals() {
        UpdateUserRequestInfo u1 = new UpdateUserRequestInfo("tech@tech.com", "Password", "Name", "Last", "Street",
                "Apt1", "City", "State", 9897,
                new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes()));
        // UpdateUserRequestInfo u2=new
        // UpdateUserRequestInfo("tech@tech.com","Password","Name","Last","Street","Apt1","City","State",9897,new
        // MockMultipartFile( "file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello,
        // World!".getBytes()));

        assertEquals(u1, u1);
        assertTrue(u1.hashCode() == u1.hashCode());
    }

    // @Test
    // @Order(2)
    // @DisplayName("2. Test toString.")
    // void testtoString() {
    // UpdateUserRequestInfo u1=new
    // UpdateUserRequestInfo("tech@tech.com","Password","Name","Last","Street","Apt1","City","State",9897,new
    // MockMultipartFile( "file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello,
    // World!".getBytes()));
    // assertEquals("UpdateUserRequestInfo(email=tech@tech.com, password=Password,
    // firstName=Name, lastName=Last, street_address=Street, apt=Apt1, city=City,
    // state=State, zipcode=9897,
    // picture=org.springframework.mock.web.MockMultipartFile@57250572)",
    // u1.toString());
    // }
    @Test
    @Order(3)
    @DisplayName("3. Test Setters.")
    void testsetter() {
        UpdateUserRequestInfo u1 = new UpdateUserRequestInfo();
        u1.setApt("Apt1");
        u1.setCity("City");
        u1.setEmail("Email");
        u1.setFirstName("Name");
        u1.setLastName("Last");
        u1.setPassword("Password");
        u1.setPicture(
                new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes()));
        u1.setState("State");
        u1.setStreet_address("Address");
        u1.setZipcode(6787);

        assertTrue(u1.getApt() == "Apt1");
        assertTrue(u1.getCity() == "City");
        assertTrue(u1.getEmail() == "Email");
        assertTrue(u1.getFirstName() == "Name");
        assertTrue(u1.getLastName() == "Last");
        assertTrue(u1.getPassword() == "Password");
        assertTrue(u1.getState() == "State");
        assertTrue(u1.getStreet_address() == "Address");
        assertTrue(u1.getZipcode() == 6787);
        // assertTrue(u1.getPicture()==new MockMultipartFile("file", "hello.txt",
        // MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes()));
    }

}