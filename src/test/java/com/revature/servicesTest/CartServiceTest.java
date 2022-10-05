package com.revature.servicesTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Order;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.Cart;
import com.revature.repositories.CartItemRepository;
import com.revature.repositories.CartRepository;
import com.revature.services.CartService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartServiceTest {
    @Mock 
    private static CartRepository mockcart;
    private static CartItemRepository mockcartitem;

    @InjectMocks
	private static CartService cartServ;

    private static Cart cart1, cart2,cart3;    
	private static List<Cart> dummyDb;

    @BeforeAll
	static void setUpBeforeClass() throws Exception{
        mockcart = Mockito.mock(CartRepository.class);
        mockcartitem=Mockito.mock(CartItemRepository.class);
        cartServ = new CartService(mockcart,mockcartitem);

        cart1 = new Cart(1, LocalDate.now(), 1);
        cart2 = new Cart(2,LocalDate.now(), 2);

        dummyDb = new ArrayList<Cart>();

        dummyDb.add(cart1);
        dummyDb.add(cart2);
    
    }

    @Test
	@Order(1)
	@DisplayName("1. Mock Validation Sanity Test")
	void checkMockInjection() {
		assertThat(mockcart).isNotNull();
		assertThat(mockcartitem).isNotNull();
		assertThat(cartServ).isNotNull();
    }

    @Test
	@Order(2)
	@DisplayName("2. Create Cart")
	void TestCreateCart() {

        cart3 = new Cart(3,LocalDate.now(), 3);

		when(mockcart.save(cart3)).thenReturn(cart3);

		assertEquals(cart3, cartServ.create(cart3));
	
	}
    @Test
	@Order(3)
	@DisplayName("3. Get all Carts")
	void TestfindAll() {

        when(cartServ.findAll()).thenReturn(dummyDb);
        assertEquals(dummyDb, cartServ.findAll());

    }

    @Test
	@Order(4)
	@DisplayName("4. Get Cart by Id")
	void TestfindById() {

        when(cartServ.findById(cart1.getId())).thenReturn(cart1);
        assertEquals(cart1, cartServ.findById(cart1.getId()));

    }

    @Test
	@Order(5)
	@DisplayName("5. Update Cart")
	void TestUpdateCart() {

        cart1.setTotalQuantity(4);

        when(cartServ.findById(cart1.getId())).thenReturn(cart1);
		when(mockcart.save(cart1)).thenReturn(cart1);
		
		assertEquals(true, cartServ.update(cart1));


    }
    @Test
	@Order(6)
	@DisplayName("6. Delete Cart")
	void TestDeleteCart() {
        doNothing().when(mockcart).delete(cart1);
        //act + assert step
        assertEquals(true, cartServ.delete(cart1));
        
	}





    
}
