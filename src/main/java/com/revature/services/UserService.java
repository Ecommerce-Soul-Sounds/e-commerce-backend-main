package com.revature.services;

import com.revature.models.Address;
import com.revature.models.User;
import com.revature.repositories.UserAddressRepository;
import com.revature.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  UserAddressRepository userAddressRepository;

    public UserService(UserRepository userRepository, UserAddressRepository userAddressRepository) {
		super();
		this.userRepository = userRepository;
		this.userAddressRepository = userAddressRepository;
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
    public Address findById(int id){
        return userAddressRepository.getById(id);
    }
    public boolean updateAddress(Address address){
        Address target = userAddressRepository.getById(address.getId());

		target.setLine1(address.getLine1());
        target.setLine2(address.getLine2());
        target.setCity(address.getCity());
        target.setState(address.getState());
        target.setZipcode(address.getId());
		target.setId(address.getId());
		
        return userAddressRepository.save(target) != null;
    }

    public boolean deleteUser(User user) {
        userRepository.delete(user);
        return true;
    }
}
