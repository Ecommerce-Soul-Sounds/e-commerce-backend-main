package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.models.User;
import com.revature.services.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Authorized
    @PutMapping("/update")
    public @ResponseBody String updateUser(@RequestBody User user, HttpServletRequest request) throws IOException, ServletException {

        /* This is retrieving the submitted profile picture and setting it as a byte array to the User model */
        Part filePart = request.getPart("picture");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        if (fileName != null) {
            byte[] bytes;

            InputStream fileContent = filePart.getInputStream();

            bytes = IOUtils.toByteArray(fileContent);

            user.setPicture(bytes);
        }

        if (userService.updateUser(user) > 0) {
            return "User updated successfully.";
        } else {
            return "User update failed. Please try again.";
        }

    }
}
