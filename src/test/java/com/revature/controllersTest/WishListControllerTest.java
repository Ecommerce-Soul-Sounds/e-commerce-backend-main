package com.revature.controllersTest;

import static com.revature.util.ClientMessageUtil.UPDATE_SUCCESSFUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.controllers.WishlistController;
import com.revature.models.Address;
import com.revature.models.Cart;
import com.revature.models.ClientMessage;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.models.WishlistItem;
import com.revature.services.WishlistService;
import com.revature.util.ClientMessageUtil;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WishlistController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WishListControllerTest {

    private static Wishlist mockWishlist1, mockWishlist2;
    private static WishlistItem mockWishlistItem1, mockWishlistItem2;
    private static Product mockProduct1, mockProduct2;
    private static List<Wishlist> dummyDb;
    private static List<WishlistItem> dummyDb2;

    ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Autowired
    WishlistController wishlistController;

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private static WishlistService wishlistService;

    @SuppressWarnings("deprecation")
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
        mockWishlist1 = new Wishlist(1, LocalDate.now());
        mockWishlist2 = new Wishlist(2, LocalDate.now());

        mockProduct1 = new Product(1, 1, 1.00, "cat1", "brand1", "desc1", "image1", "name1");
        mockProduct1 = new Product(2, 2, 2.00, "cat2", "brand2", "desc2", "image2", "name2");

        mockWishlistItem1 = new WishlistItem(1, mockProduct1, mockWishlist1);
        mockWishlistItem2 = new WishlistItem(2, mockProduct2, mockWishlist2);

        dummyDb = new ArrayList<>();
        dummyDb.add(mockWishlist1);
        dummyDb.add(mockWishlist2);

        dummyDb2 = new ArrayList<>();
        dummyDb2.add(mockWishlistItem1);
        dummyDb2.add(mockWishlistItem2);
    }

    @Test
    @Order(1)
    @DisplayName("1. AppContext Sanity Test")
    public void contextLoads() throws Exception {

        assertThat(wishlistController).isNotNull();

    }

    @Test
    @Order(2)
    @DisplayName("2. Get user wishlist")
    public void testGetUserWishlist() throws Exception {
        Cart cart1 = new Cart(1, LocalDate.now(), 1);
        Address addy = new Address(1, "line1", "line2", "city", "state", 00000);
        User user = new User(1, "user1", "email", "first", "last", new byte[1024], addy, mockWishlist1, cart1);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        when(wishlistService.getWishlistById(mockWishlist1.getId())).thenReturn((mockWishlist1));
        RequestBuilder request = MockMvcRequestBuilders.get("/api/wishlist")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(om.writeValueAsString(mockWishlist1.getId()))
                .contentType(MediaType.APPLICATION_JSON).session(session);
        MvcResult result = mockmvc.perform(request).andReturn();
        assertEquals(om.writeValueAsString(mockWishlist1), result.getResponse().getContentAsString());

    }

    @Test
    @Order(3)
    @DisplayName("3. Get wishlist items")
    public void testGetWishlistItems() throws Exception {
        Cart cart1 = new Cart(1, LocalDate.now(), 1);
        Address addy = new Address(1, "line1", "line2", "city", "state", 00000);
        User user = new User(1, "user1", "email", "first", "last", new byte[1024], addy, mockWishlist1, cart1);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        when(wishlistService.getWishlistItemsByWishlist(mockWishlist1)).thenReturn((dummyDb2));
        RequestBuilder request = MockMvcRequestBuilders.get("/api/wishlist/items")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(om.writeValueAsString(mockWishlist1))
                .contentType(MediaType.APPLICATION_JSON).session(session);
        MvcResult result = mockmvc.perform(request).andReturn();
        assertEquals(om.writeValueAsString(dummyDb2), result.getResponse().getContentAsString());

    }

    // @Test
    // @Order(4)
    // @DisplayName("4. Delete wishlist item")
    // void testDeleteWishlistItem() throws Exception {
    //     Cart cart1 = new Cart(1, LocalDate.now(), 1);
    //     Address addy = new Address(1, "line1", "line2", "city", "state", 00000);
    //     User user = new User(1, "user1", "email", "first", "last", new byte[1024], addy, mockWishlist1, cart1);

    //     MockHttpSession session = new MockHttpSession();
    //     session.setAttribute("user", user);

    //     when(wishlistService.deleteWishlistItem(mockWishlist1, mockProduct1.getId())).thenReturn((true));
    //     RequestBuilder request = MockMvcRequestBuilders.delete("/api/wishlist/delete-item")
    //             .accept(MediaType.APPLICATION_JSON_VALUE)
    //             .content(om.writeValueAsString(mockProduct1.getId()))
    //             .contentType(MediaType.APPLICATION_JSON).session(session);
    //     MvcResult result = mockmvc.perform(request).andReturn();
    //     assertEquals((om.writeValueAsString([])), result.getResponse().getContentAsString());

    // }

    @Test
    @Order(5)
    @DisplayName("5. Add item to wishlist")
    void testAddWishlistItem() throws Exception {
        Cart cart1 = new Cart(1, LocalDate.now(), 1);
        Address addy = new Address(1, "line1", "line2", "city", "state", 00000);
        User user = new User(1, "user1", "email", "first", "last", new byte[1024], addy, mockWishlist1, cart1);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        when(wishlistService.addWishlistItem(mockWishlist1, mockProduct1.getId())).thenReturn((true));
        RequestBuilder request = MockMvcRequestBuilders.post("/api/wishlist/add-item")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(om.writeValueAsString(mockProduct1.getId()))
                .contentType(MediaType.APPLICATION_JSON).session(session);
        MvcResult result = mockmvc.perform(request).andReturn();
        assertEquals("{"+"\"message\""+":"+"\"ITEM SUCCESSFULLY ADDED\""+"}", result.getResponse().getContentAsString());

    }

}
