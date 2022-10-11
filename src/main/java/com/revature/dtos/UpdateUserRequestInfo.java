package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestInfo {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String street_address;
    private String apt;
    private String city;
    private String state;
    private int zipcode;
}
