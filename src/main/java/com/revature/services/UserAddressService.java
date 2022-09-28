package com.revature.services;

import org.springframework.stereotype.Service;

import com.revature.models.Address;
import com.revature.repositories.UserAddressRepository;

@Service
public class UserAddressService {
    private final UserAddressRepository userAddressRepo;

    public UserAddressService(UserAddressRepository userAddressRepo){
        this.userAddressRepo=userAddressRepo;
    }

    public Address create(Address address){
        return userAddressRepo.save(address);
    }

    public Address findById(int id){
        return userAddressRepo.getById(id);
    }
    public boolean update(Address address){
        return userAddressRepo.update(address.getLine1(),address.getLine2(),address.getCity(),address.getState(),address.getZipcode(),address.getId());

    }

    public boolean delete(Address address){
        userAddressRepo.delete(address);
        return true; 
    }

    
}
