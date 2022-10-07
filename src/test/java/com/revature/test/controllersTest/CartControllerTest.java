package com.revature.test.controllersTest;

import static com.revature.util.ClientMessageUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.util.ClientMessageUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.datatype.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.CartController;
import com.revature.dtos.CartDTO;
import com.revature.models.Cart;
import com.revature.models.CartItem;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.services.CartService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartControllerTest {
    private static CartDTO mockCart1, mockCart2;
    private static CartDTO mockCartCreation;
    private static CartDTO mockCartModification;
    private static CartDTO mockCartDeletion;
    private static CartItem mockCartItem1, mockCartItem2, mockCartItem3;
    private static Cart cart1, cart2;
    private static Product mockProduct1, mockProduct2;
    private static User u1;

    private static List<Cart> dummyDb;
    private static List<CartItem> mockCartItemsList;

    ObjectMapper om = new ObjectMapper().registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());

    @Autowired
    CartController cartController;

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private CartService cartService;

    public boolean isValidJSON(final String json) {
        boolean valid = false;
        try {
            final JsonParser parser = new ObjectMapper().getFactory().createParser(json);
            while (parser.nextToken() != null) {
            }
            valid = true;
        } catch (JsonParseException jpe) {
            jpe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return valid;
    }

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("setUpBeforeClass() :: building test objects...");
        cart1 = new Cart();
        cart2 = new Cart();

        mockCart1 = new CartDTO(1, LocalDate.now(), 1);
        mockCart2 = new CartDTO(2, LocalDate.now(), 2);

        cart1.setId(mockCart1.getId());
        cart1.setDateModified(mockCart1.getDateModified());
        cart1.setTotalQuantity(mockCart1.getTotalQuantity());

        cart2.setId(mockCart2.getId());
        cart2.setDateModified(mockCart2.getDateModified());
        cart2.setTotalQuantity(mockCart2.getTotalQuantity());

        mockProduct1 = new Product(1, 1, 1.00, "cat1", "brand1", "des1", "image1", "name1");
        mockProduct2 = new Product(2, 2, 2.00, "cat2", "brand2", "des2", "image2", "name2");

        u1 = new User(1, "jowill@gmail.com", "jowill", "joel", "will");

        mockCartItem1 = new CartItem(1, 2, mockProduct1, cart1);
        mockCartItem2 = new CartItem(2, 2, mockProduct2, cart1);
        mockCartItem3 = new CartItem(2, 2, mockProduct2, cart2);
        dummyDb = new ArrayList<Cart>();
        dummyDb.add(cart1);
        dummyDb.add(cart2);

        mockCartItemsList = new ArrayList<>();
        mockCartItemsList.add(mockCartItem1);
    }

    @Test
    @Order(1)
    @DisplayName("1. AppContext Sanity Test")
    public void contextLoads() throws Exception {
        assertThat(cartController).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("2. Create Cart - Happy Path Scenerio Test")
    public void testCreateCart() throws Exception {
        mockCartCreation = new CartDTO(3, LocalDate.now(), 3);
        Cart cart3 = new Cart();

        cart3.setId(mockCartCreation.getId());
        cart3.setDateModified(mockCartCreation.getDateModified());
        cart3.setTotalQuantity(mockCartCreation.getTotalQuantity());

        when(cartService.create(cart3)).thenReturn(cart3);
        // act
        RequestBuilder request = MockMvcRequestBuilders.post("/api/cart/create-cart")
                .accept(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(cart3))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockmvc.perform(request).andReturn();
        // assert
        om.setDateFormat(new SimpleDateFormat());
        assertEquals(om.writeValueAsString(cart3), result.getResponse().getContentAsString());

    }

    @Test
    @Order(3)
    @DisplayName("3. Get Cart by Id - Happy Path Scenerio Test")
    public void testCartById() throws Exception {

         MockHttpSession session = new MockHttpSession();
         u1.setCart(cart1);
         session.setAttribute("user", u1);
    

        when(cartService.findById(cart1.getId())).thenReturn((dummyDb.get(0)));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/cart/get-cart")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON).session(session);
        MvcResult result = mockmvc.perform(request).andReturn();

        om.setDateFormat(new SimpleDateFormat());
        assertEquals(om.writeValueAsString(dummyDb.get(0)), result.getResponse().getContentAsString());

    }

    @Test
    @Order(4)
    @DisplayName("4. Delete Cart Test")
    public void testdeleteCart() throws Exception {

        when(cartService.delete(cart1)).thenReturn(true);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/cart/delete-cart")
                .accept(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(mockCart1))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockmvc.perform(request).andReturn();

        om.setDateFormat(new SimpleDateFormat());
        assertEquals(om.writeValueAsString(DELETION_SUCCESSFUL), result.getResponse().getContentAsString());

    }

//    @Test
//    @Order(5)
//    @DisplayName("5. Add CartItem Test")
//    public void testAddCartItem() throws Exception {
//
//        MockHttpSession session = new MockHttpSession();
//        u1.setCart(cart1);
//        session.setAttribute("user", u1);
//
//        when(cartService.update(u1.getCart())).thenReturn(true);
//        when(cartService.addCartItem(u1.getCart(), 1, 1)).thenReturn(true);
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/cart/add-item")
//                .accept(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(1))
//                .contentType(MediaType.APPLICATION_JSON).session(session);
//        MvcResult result = mockmvc.perform(request).andReturn();
//
//        om.setDateFormat(new SimpleDateFormat());
//        assertEquals("Item added to Cart", result.getResponse().getContentAsString());
//
//    }

    @Test
    @Order(6)
    @DisplayName("6. Delete CartItem Test")
    public void testDeleteCartItem() throws Exception {

        MockHttpSession session = new MockHttpSession();
        u1.setCart(cart1);
        session.setAttribute("user", u1);

        when(cartService.deleteCartItem(u1.getCart(), 1)).thenReturn(true);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/cart/delete-item")
                .accept(MediaType.APPLICATION_JSON_VALUE).content(om.writeValueAsString(1))
                .contentType(MediaType.APPLICATION_JSON).session(session);
        MvcResult result = mockmvc.perform(request).andReturn();

        om.setDateFormat(new SimpleDateFormat());
        assertEquals("Item successfully removed from your Cart.", result.getResponse().getContentAsString());

    }

    @Test
    @Order(7)
    @DisplayName("7. Get Cart Items Test")
    public void testGetCartItems() throws Exception {
        MockHttpSession session = new MockHttpSession();
        u1.setCart(cart1);
        session.setAttribute("user", u1);


        when(cartService.getCartItemsByCartId(u1.getId())).thenReturn(mockCartItemsList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/cart/items")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON).session(session);
        MvcResult result = mockmvc.perform(request).andReturn();

        om.setDateFormat(new SimpleDateFormat());
        assertEquals(om.writeValueAsString(mockCartItemsList), result.getResponse().getContentAsString());
    }

}