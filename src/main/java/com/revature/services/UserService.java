package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.findUserById(id);
    }

    public int updateUser(User user) {
        return userRepository.updateUser(user.getFirstName(), user.getLastName(),
                                        user.getEmail(), user.getPassword(), user.getPicture(),
                                        user.getAddress().getId(), user.getWishlist().getId(),
                                        user.getCart().getId(), user.getId());
    }

    public int updateUserCart(User user) {
        return userRepository.updateUserCart(user.getCart().getId(), user.getId());
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
