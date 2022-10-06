package com.revature.servicesTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Order;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;
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
import com.revature.repositories.CartRepository;
import com.revature.repositories.ProductRepository;
import com.revature.services.CartService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartServiceTest {
    @Mock 
    private static CartRepository mockcart;
    @Mock 
    private static CartItemRepository mockcartitem;
    @Mock 
    private static ProductRepository mockProductDao;

    @InjectMocks
	private static CartService cartServ;

    private static Cart cart1, cart2,cart3;
    private static CartItem cartItem1, cartItem2,cartItem3;
    private static Product product1, product2,product3;         
	private static List<Cart> dummyDb;
    private static List<CartItem> dummyDb2;

    @BeforeAll
	static void setUpBeforeClass() throws Exception{
        mockcart = Mockito.mock(CartRepository.class);
        mockcartitem=Mockito.mock(CartItemRepository.class);
        cartServ = new CartService(mockcart,mockcartitem);

        cart1 = new Cart(1, LocalDate.now(), 1);
        cart2 = new Cart(2,LocalDate.now(), 2);

        product1 = new Product(1, 1, 1.00, "cat", "band", "desc", "image", "name1");
        product2 = new Product(2, 1, 2.00, "cat2", "band2", "desc2", "image2", "name2");
        product3 = new Product(3, 1, 3.00, "cat3", "band3", "desc3", "image3", "name3");
        
        cartItem1 = new CartItem(1, 1, product1, cart1);
        cartItem2 = new CartItem(2, 2, product2, cart2);
        cartItem3 = new CartItem(3, 3, product3, cart3);

        dummyDb = new ArrayList<Cart>();
        dummyDb2 = new ArrayList<CartItem>();


        dummyDb.add(cart1);
        dummyDb.add(cart2);

        dummyDb2.add(cartItem1);
        dummyDb2.add(cartItem2);
        dummyDb2.add(cartItem3);
    
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

    @Test
    @Order(7)
    @DisplayName("7. Test get cart items by Cart id")
    void TestGetCartItemsByCartId() {
        
        when(mockcartitem.getCartItemsByCartId(cartItem1.getCart().getId())).thenReturn(dummyDb2);

        assertEquals(dummyDb2, cartServ.getCartItemsByCartId(1));
    }

    @Test
    @Order(8)
    @DisplayName("8. Test add item to cart")
    void TestAddCartItem() {
        CartItem item = new CartItem();
    	item.setCart(cart1);
    	item.setProduct(product1);
        when(mockcartitem.save(item)).thenReturn(cartItem1);
    	when(mockProductDao.findById(1)).thenReturn(Optional.of(product1));

        assertEquals(true, cartServ.addCartItem(cart1, 1));
    }

    @Test
    @Order(9)
    @DisplayName("9. Test remove items from cart")
    void TestDeleteCartItem() {
    	CartItem item = new CartItem();
    	item.setCart(cart1);
    	item.setProduct(product1);
    	when(mockProductDao.findById(1)).thenReturn(Optional.of(product1));
        assertEquals(true, cartServ.deleteCartItem(cart1, 1));
    }







    
}
