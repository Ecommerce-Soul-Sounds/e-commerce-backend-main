package com.revature.servicesTest;

import com.revature.models.Address;
import com.revature.models.Cart;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.repositories.UserAddressRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {
    @Mock
    private static UserRepository mockUserDao;
    @Mock
    private static UserAddressRepository mockUserAddressDao;

    @InjectMocks
    private static UserService userService;
    private static Address address1;
    private static Wishlist wishlist1;
    private static Cart cart1;
    private static byte[] picture1;
    private static User u1;

    @BeforeAll
    static void setUpBeforeClass() {
        mockUserDao = Mockito.mock(UserRepository.class);
        mockUserAddressDao = Mockito.mock(UserAddressRepository.class);
        userService = new UserService(mockUserDao, mockUserAddressDao);

        address1 = new Address(1, "line1", "line2", "city1", "state1", 123);

        wishlist1 = new Wishlist(1, LocalDate.now());
        wishlist1 = new Wishlist(2, LocalDate.now());

        cart1 = new Cart(1, LocalDate.now(), 1);
        cart1 = new Cart(2, LocalDate.now(), 2);

        picture1 = new byte[] { 1, 2, 3 };

        u1 = new User(1, "jowill@gmail.com", "jowill", "joel", "will", new byte[1], address1, wishlist1, cart1);

    }

    @Test
    @Order(1)
    @DisplayName("1. Mock validation Sanity Test")
    void checkMockInjection() {
        assertThat(mockUserDao).isNotNull();
        assertThat(mockUserAddressDao).isNotNull();
        assertThat(userService).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("2. Get User By Id")
    void testGetUserById() {
        User newUser = new User(3, "email3", "password3", "firstName3", "lastName", picture1, address1, wishlist1,
                cart1);

        when(userService.getUserById(3)).thenReturn(newUser);

        assertEquals(newUser, userService.getUserById(3));
    }

    @Test
    @Order(3)
    @DisplayName("3. Update User Cart")
    void testUpdateUserCart() {
        User newUser = new User(3, "email3", "password3", "firstName3", "lastName", picture1, address1, wishlist1,
                cart1);

        when(userService.updateUserCart(newUser)).thenReturn(cart1.getId());

        assertEquals(newUser.getCart().getId(), userService.updateUserCart(newUser));
    }

    @Test
    @Order(4)
    @DisplayName("4. Update User Address")
    void testUpdateUserAddress() {

        address1.setCity("TestCity");
        address1.setState("TestState");

        when(userService.findById(address1.getId())).thenReturn(address1);
        when(mockUserAddressDao.save(address1)).thenReturn(address1);

        assertEquals(true, userService.updateAddress(address1));
    }

    @Test
    @Order(5)
    @DisplayName("5. Update user.")
    void testUpdateUser() {

        u1.setFirstName("newName");
        u1.setEmail("newEmail");

        when(userService.updateUser(u1)).thenReturn(1);

        assertEquals(1, userService.updateUser(u1));
    }

    @Test
    @Order(6)
    @DisplayName("6. Find user by credentials.")
    void testFindByCredentials() {
        when(userService.findByCredentials(u1.getEmail(),u1.getPassword())).thenReturn(Optional.of(u1));

        assertEquals(Optional.of(u1), userService.findByCredentials(u1.getEmail(),u1.getPassword()));
    }
    @Test
    @Order(7)
    @DisplayName("7. Save user.")
    void  testSave() {
        when(userService.save(u1)).thenReturn(u1);

        assertEquals(u1, userService.save(u1));
    }
    @Test
    @Order(8)
    @DisplayName("8. Delete user.")
    void  testDelete() {
        doNothing().when(mockUserDao).delete(u1);
		assertEquals(true, userService.deleteUser(u1));
    }

}