package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.UpdateUserRequestInfo;
import com.revature.models.Address;
import com.revature.models.ClientMessage;
import com.revature.models.User;
import com.revature.services.UserAddressService;
import com.revature.services.UserService;
import com.revature.util.ClientMessageUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserAddressService addressService;

    @Authorized
    @PutMapping("/update")
    public @ResponseBody ClientMessage updateUser(@RequestPart(required = false) UpdateUserRequestInfo userUpdateInfo, @RequestPart(required = false) MultipartFile picture, HttpServletRequest request) throws IOException, ServletException {

        User loggedInUser = (User) request.getSession().getAttribute("user");


        User updateUser = new User();
        updateUser.setId(loggedInUser.getId());

        if (userUpdateInfo != null) {
            loggedInUser.setFirstName(userUpdateInfo.getFirstName());
            loggedInUser.setLastName(userUpdateInfo.getLastName());
            loggedInUser.setEmail(userUpdateInfo.getEmail());
            loggedInUser.setPassword(userUpdateInfo.getPassword());

            Address receivedAddress = new Address();
            receivedAddress.setId(loggedInUser.getAddress().getId());
            receivedAddress.setLine1(userUpdateInfo.getStreet_address());
            receivedAddress.setLine2(userUpdateInfo.getApt());
            receivedAddress.setCity(userUpdateInfo.getCity());
            receivedAddress.setState(userUpdateInfo.getState());
            receivedAddress.setZipcode(userUpdateInfo.getZipcode());

            if (addressService.update(receivedAddress)) {
                loggedInUser.setAddress(receivedAddress);
            }
        }

        /* This is retrieving the submitted profile picture and setting it as a byte array to the User model */
        if (picture != null && (!(picture.isEmpty()))) {
            byte[] profilePicBytes = picture.getBytes();
            loggedInUser.setPicture(profilePicBytes);
        }

        if (userService.updateUser(loggedInUser) > 0) {
            return ClientMessageUtil.UPDATE_SUCCESSFUL;
        } else {
            return ClientMessageUtil.UPDATE_FAILED;
        }
        /* This is retrieving the submitted profile picture and setting it as a byte array to the User model */
//        Part filePart = request.getPart("picture");
//        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//        if (fileName != null) {
//            byte[] bytes;
//
//            InputStream fileContent = filePart.getInputStream();
//
//            bytes = IOUtils.toByteArray(fileContent);
//
//            user.setPicture(bytes);
//        }

    }
}
