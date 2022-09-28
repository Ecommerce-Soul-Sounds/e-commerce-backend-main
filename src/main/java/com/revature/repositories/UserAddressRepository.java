package com.revature.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.models.Address;

public interface UserAddressRepository extends JpaRepository<Address, Integer> {
    
    //update address by id 
    @Query(value = "UPDATE address SET street_address_line_one = ?1, street_address_line_two = ?2, city = ?3, state = ?4, zipcode = ?5, WHERE address_id = ?6", nativeQuery = true)
    public boolean update(String street_address_line_one, String street_address_line_two, String city, String State, int zipcode, int id);
}
