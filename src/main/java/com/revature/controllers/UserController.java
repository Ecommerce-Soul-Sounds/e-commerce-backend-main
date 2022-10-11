package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.UpdateUserRequestInfo;
import com.revature.exceptions.UpdateUserException;
import com.revature.models.Address;
import com.revature.models.ClientMessage;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.ClientMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class UserController {
    @Autowired
    private UserService userService;


    @Authorized
    @PutMapping(value = "/update")
    @CrossOrigin(methods = RequestMethod.PUT)
    public @ResponseBody ClientMessage updateUser(@RequestPart(name = "userUpdateInfo", required = false) UpdateUserRequestInfo userUpdateInfo, @RequestPart(value = "picture", required = false) @Valid @NotNull MultipartFile picture, HttpServletRequest request) throws IOException, ServletException {

        System.out.println(userUpdateInfo);

        User loggedInUser = (User) request.getSession().getAttribute("user");



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

            if (userService.updateAddress(receivedAddress)) {
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
            throw new UpdateUserException("User could not be updated at this time. Please try again.");
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
