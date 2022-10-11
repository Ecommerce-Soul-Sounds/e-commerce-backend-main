package com.revature.servicesTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

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

import com.revature.models.Address;
import com.revature.models.Cart;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.repositories.CartRepository;
import com.revature.repositories.UserAddressRepository;
import com.revature.repositories.WishlistRepository;
import com.revature.services.AuthService;
import com.revature.services.UserService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceTest {
    @Mock
	private static UserService mockdao;
    @Mock
    private CartRepository cartRepo;
    
    @Mock
    private WishlistRepository wishlistRepo;
    
    @Mock
    private UserAddressRepository addressRepo;
    
    @InjectMocks
    private static AuthService authServ;
    private static User user1,user2;
    private static Address address1,address2;
    private static Wishlist wishlist1, wishlist2;
    private static Cart cart1,cart2;
    private static  byte[] picture1, picture2;
    private static List<User> dummyDb;

    @BeforeAll
	static void setUpBeforeClass() throws Exception{
        mockdao = Mockito.mock(UserService.class);
        authServ = new AuthService(mockdao);
        
        address1= new Address(1, "line1", "line2", "city1", "state1", 123);
        address2= new Address(2, "line1", "line2", "city2", "state2", 321);
        
        wishlist1 = new Wishlist(1, LocalDate.now());
        wishlist1 = new Wishlist(2, LocalDate.now());
        
        cart1 = new Cart(1, LocalDate.now(), 1);
        cart1 = new Cart(2, LocalDate.now(), 2);

        picture1 =
        new byte[] {1,2,3};

        picture2 =
        new byte[] {3,2,1};
        
        user1 = new User(1, "user1", "password1", "firstName1", "lastName2", picture1, address1, wishlist1, cart1); 
        user2 = new User(2, "user2", "password2", "firstName2", "lastName2", picture2, address2, wishlist2, cart2); 

        dummyDb = new ArrayList <User>();
		dummyDb.add(user1);
		dummyDb.add(user2);

    }

    @Test
	@Order(1)
	@DisplayName("1. Mock Validation Sanity Test")
	void checkMockInjection() {
        mockdao = Mockito.mock(UserService.class);
		assertThat(authServ).isNotNull();
    }

    @Test
	@Order(2)
	@DisplayName("2.Find By Credentials Test")
	void testFindByCredentials() {
        Optional<User> expected = Optional.of(user1);

        when(authServ.findByCredentials(expected.get().getEmail(),expected.get().getPassword())).thenReturn(expected);
        
        assertEquals(user1, authServ.findByCredentials(user1.getEmail(), user1.getPassword()).get());

    }
   @Test
	@Order(3)
	@DisplayName("3.Register Test")
	void testRegister() {
       User newUser = new User(3, "email3", "password3", "firstName3", "lastName", picture1, address2, new Wishlist(), new Cart());
       
       when(mockdao.save(newUser)).thenReturn(newUser);
       when(cartRepo.save(new Cart())).thenReturn(new Cart());
       when(addressRepo.save(address2)).thenReturn(address2);
       when(wishlistRepo.save(new Wishlist())).thenReturn(new Wishlist());
       when(authServ.register(newUser,address2)).thenReturn(newUser);
       
       assertEquals(newUser, authServ.register(newUser,address2));

   }
    
}