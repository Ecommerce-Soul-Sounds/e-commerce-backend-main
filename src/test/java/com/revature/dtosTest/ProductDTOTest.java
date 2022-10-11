package com.revature.dtosTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.revature.dtos.ProductDTO;

public class ProductDTOTest {
    @Test
    @Order(1)
    @DisplayName("1. Test equals and Hashcode.")
    void testequals() {
        ProductDTO p1 = new ProductDTO(1, 2, 45.54, "Piano", "Description", "Brand", "Image", "Name");
        ProductDTO p2 = new ProductDTO(1, 2, 45.54, "Piano", "Description", "Brand", "Image", "Name");

        assertEquals(p2, p1);
        assertTrue(p1.hashCode() == p2.hashCode());
    }

    @Test
    @Order(2)
    @DisplayName("2. Test toString.")
    void testtoString() {
        ProductDTO p1 = new ProductDTO(1, 2, 45.54, "Piano", "Description", "Brand", "Image", "Name");

        assertEquals(
                "ProductDTO(id=1, quantity=2, price=45.54, category=Piano, brand=Description, description=Brand, image=Image, name=Name)",
                p1.toString());
    }

    @Test
    @Order(3)
    @DisplayName("3. Test Setters.")
    void testsetter() {
        ProductDTO p1 = new ProductDTO();
        p1.setId(1);
        p1.setQuantity(4);
        p1.setBrand("Brand");
        p1.setCategory("Piano");
        p1.setDescription("Description");
        p1.setImage("Image");
        p1.setName("Name");
        p1.setPrice(46.54);

        assertTrue(p1.getId() == 1);
        assertTrue(p1.getQuantity() == 4);
        assertTrue(p1.getBrand() == "Brand");
        assertTrue(p1.getCategory() == "Piano");
        assertTrue(p1.getDescription() == "Description");
        assertTrue(p1.getImage() == "Image");
        assertTrue(p1.getName() == "Name");
        assertTrue(p1.getPrice() == 46.54);

    }

}