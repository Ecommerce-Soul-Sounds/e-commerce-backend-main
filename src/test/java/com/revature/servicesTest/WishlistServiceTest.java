package com.revature.servicesTest;

import com.revature.models.Product;
import com.revature.models.Wishlist;
import com.revature.models.WishlistItem;
import com.revature.repositories.ProductRepository;
import com.revature.repositories.WishlistItemRepository;
import com.revature.repositories.WishlistRepository;
import com.revature.services.WishlistService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WishlistServiceTest {
    @Mock
    private static WishlistRepository mockWishlistDao;
    
    @Mock
    private static WishlistItemRepository mockWishlistItemDao;
    
    @Mock
    private static ProductRepository mockProductDao;

    @InjectMocks
    private static WishlistService wishlistService;
    
    private static Wishlist wishlist1;
    private static Product  product1;
    private static WishlistItem item1;

    
    @BeforeAll
    static void setupBeforeClass() {
        wishlist1 = new Wishlist(1, LocalDate.now());
        product1 = new Product(1, 10, 100, "Instrument", "TrumpetsRUs", "A Trumpet", null, "Trumpet");
        item1 = new WishlistItem(1, product1, wishlist1);
    }

    @Test
    @Order(1)
    @DisplayName("1. Mock validation Sanity Test")
    void checkMockInjection() {
        assertThat(mockWishlistDao).isNotNull();
        assertThat(mockWishlistDao).isNotNull();
        assertThat(mockWishlistItemDao).isNotNull();
        assertThat(mockProductDao).isNotNull();
        assertThat(wishlistService).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("2. Test add new wishlist")
    void testAddWishlist() {
        Wishlist wishlist = new Wishlist(1, LocalDate.now());

        when(mockWishlistDao.save(wishlist)).thenReturn(wishlist);

        assertEquals(wishlist, wishlistService.addWishlist(wishlist));
    }

    @Test
    @Order(3)
    @DisplayName("3. Test update wishlist")
    void testUpdateWishlist() {
        when(mockWishlistDao.updateWishlist(LocalDate.now(), 1)).thenReturn(1);

        assertEquals(1, wishlistService.updateWishlist(wishlist1));
    }

    @Test
    @Order(4)
    @DisplayName("4. Test Get wishlist by Id")
    void testGetWishlistById() {
        Wishlist wishlist = new Wishlist(1, LocalDate.now());

        when(mockWishlistDao.getWishlistById(1)).thenReturn(wishlist1);

        assertEquals(wishlist, wishlistService.getWishlistById(1));
    }
    
    @Test
    @Order(5)
    @DisplayName("5. Test add to wishlist")
    void testAddToWishlist() {	
    	WishlistItem item = new WishlistItem();
    	item.setWishlist(wishlist1);
    	item.setProduct(product1);
    	when(mockWishlistItemDao.save(item)).thenReturn(item1);
    	when(mockProductDao.findById(1)).thenReturn(Optional.of(product1));
    	
    	assertTrue(wishlistService.addWishlistItem(wishlist1, 1));
    }
    
    @Test
    @Order(6)
    @DisplayName("6. Test remove from wishlist")
    void testRemoveFromWishlist() {
    	WishlistItem item = new WishlistItem();
    	item.setWishlist(wishlist1);
    	item.setProduct(product1);
    	when(mockProductDao.findById(1)).thenReturn(Optional.of(product1));
    	assertTrue(wishlistService.deleteWishlistItem(wishlist1, 1));
    }
    
    @Test
    @Order(7)
    @DisplayName("7. Test get all items for wishlist")
    void testGetAllWishlistItemsByWishlist() {
    	List<WishlistItem> list = new ArrayList<>();
    	list.add(item1);
    	when(mockWishlistItemDao.getWishlistItemsByWishlistID(1)).thenReturn(list);
    	assertEquals(list , wishlistService.getWishlistItemsByWishlist(wishlist1));
    }
}