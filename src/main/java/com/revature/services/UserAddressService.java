package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Address;
import com.revature.repositories.UserAddressRepository;

@Service
public class UserAddressService {
    @Autowired
    private UserAddressRepository userAddressRepo;

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
        Address target = userAddressRepo.getById(address.getId());

		target.setLine1(address.getLine1());
        target.setLine2(address.getLine2());
        target.setCity(address.getCity());
        target.setState(address.getState());
        target.setZipcode(address.getId());
		target.setId(address.getId());
		
        return (userAddressRepo.save(target) != null) ? true : false;
    }

    public boolean delete(Address address){
        userAddressRepo.delete(address);
        return true; 
    }

    
}
