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
import com.revature.models.CartItem;
import com.revature.models.Product;
import com.revature.repositories.CartItemRepository;
import com.revature.services.CartItemService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartItemServiceTest {
    @Mock 
    private static CartItemRepository mockDao;

    @InjectMocks
	private static CartItemService cartItemServ;
    
    private static CartItem cartItem1, cartItem2,cartItem3;
	private static Product product1,product2;
	private static Cart cart1,cart2;
    
    
	private static List<CartItem> dummyDb;

    @BeforeAll
	static void setUpBeforeClass() throws Exception{

        mockDao = Mockito.mock(CartItemRepository.class);
		cartItemServ = new CartItemService(mockDao);

        product1 = new Product(1, 2, 3.23,"category", "brand", "description", "image", "name");
        product2 = new Product(2, 3, 3.24,"category2", "brand2", "description2", "image2", "name2");
        
        cart1 = new Cart(1, LocalDate.now(), 1);
        cart2 = new Cart(2,LocalDate.now(), 2);

        cartItem1 = new CartItem(1, 1, product1, cart1);
        cartItem2 = new CartItem(2, 2, product2, cart2);

        dummyDb = new ArrayList<CartItem>();

        dummyDb.add(cartItem1);
        dummyDb.add(cartItem2);

    }

    @Test
	@Order(1)
	@DisplayName("1. Mock Validation Sanity Test")
	void checkMockInjection() {
		assertThat(mockDao).isNotNull();
		assertThat(cartItemServ).isNotNull();
    }

    @Test
	@Order(2)
	@DisplayName("3. Create CartItem")
	public void TestCreateCreateItem() {

       cartItem3 = new CartItem(3, 3, product1, cart1);

		when(mockDao.save(cartItem3)).thenReturn(cartItem3);

		assertEquals(1, cartItemServ.create(cartItem3));
	
	}

    @Test
	@Order(3)
	@DisplayName("3. Get all CartItems by Id")
	void findAllByCartId() {

        when(cartItemServ.findAllByCartId(cartItem1)).thenReturn(dummyDb);
        assertEquals(dummyDb, cartItemServ.findAllByCartId(cartItem1));

    }
    @Test
	@Order(4)
	@DisplayName("4. Get CartItem by Id")
	void findById() {

        when(cartItemServ.findById(cartItem1.getId())).thenReturn(cartItem1);
        assertEquals(cartItem1, cartItemServ.findById(cartItem1.getId()));

    }

    
    @Test
	@Order(5)
	@DisplayName("5. Update CartItem")
	void testUpdateCartItem() {

        cartItem1.setQuantity(4);

        when(cartItemServ.findById(cartItem1.getId())).thenReturn(cartItem1);
		when(mockDao.save(cartItem1)).thenReturn(cartItem1);
		
		assertEquals(true, cartItemServ.update(cartItem1));


    }
    @Test
	@Order(6)
	@DisplayName("6. Delete CartItem")
	void testDeleteCartItem() {
        doNothing().when(mockDao).delete(cartItem1);
        //act + assert step
        assertEquals(true, cartItemServ.delete(cartItem1));
        
	}


}