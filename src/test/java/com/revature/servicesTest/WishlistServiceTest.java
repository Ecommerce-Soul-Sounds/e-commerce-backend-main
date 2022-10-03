package com.revature.servicesTest;

import com.revature.models.Wishlist;
import com.revature.repositories.WishlistRepository;
import com.revature.services.WishlistService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WishlistServiceTest {
    @Mock
    private static WishlistRepository mockWishlistDao;

    @InjectMocks
    private static WishlistService wishlistService;
    private static Wishlist wishlist1;

    @BeforeAll
    static void setupBeforeClass() {
        mockWishlistDao = Mockito.mock(WishlistRepository.class);

        wishlistService = new WishlistService();

        wishlist1 = new Wishlist(1, LocalDate.now());
    }

    @Test
    @Order(1)
    @DisplayName("1. Mock validation Sanity Test")
    void checkMockInjection() {
        assertThat(mockWishlistDao).isNotNull();
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
}
