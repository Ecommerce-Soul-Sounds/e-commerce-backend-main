package com.revature.repositories;

import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);

    @Query(value = "SELECT * FROM users WHERE user_id = ?1", nativeQuery = true)
    User findUserById(int id);

    @Query(value = "UPDATE users SET first_name = ?1, last_name = ?2, email = ?3, password = ?4, picture = ?5, address_id = ?6, wishlist_id = ?7, cart_id = ?8 WHERE user_id = ?9", nativeQuery = true)
    int updateUser(String firstname, String lastname, String email, String password, byte[] picture, int addressID, int wishlistID, int cartID, int userID);

    @Query(value = "UPDATE users SET cart_id = ?1 WHERE user_id = ?2", nativeQuery = true)
    int updateUserCart(int cartId, int userID);
}
