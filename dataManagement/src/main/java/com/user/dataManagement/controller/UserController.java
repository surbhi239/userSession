package com.user.dataManagement.controller;

import com.user.dataManagement.exception.LoginLogoutException;
import com.user.dataManagement.exception.UserNotFoundException;
import com.user.dataManagement.model.User;
import com.user.dataManagement.model.loginLogoutData;
import com.user.dataManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
@EnableWebMvc
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> registerUser(@RequestBody User userDetails) {

        try {
            userService.saveUser(userDetails);
            return new ResponseEntity<String>("{\"message\":\"" + "User registered successfully" + "\"}", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> loginUser(@RequestBody User loginDetail) {
        try {
            int empCode = loginDetail.getEmployeeCode();
            String password = loginDetail.getPassword();

            boolean userDetails = userService.findByEmpCodeAndPassword(empCode, password);
            if (userDetails == false) {
                throw new Exception("Username with given id does not exist");
            }
            return new ResponseEntity<String>("{\"message\":\"" + "User logged in successfully" + "\"}", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\"}", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/logout",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> logOutUser(@RequestParam("employeeCode") final int employeeCode) {
        try {
            userService.logoutUser(employeeCode);
            return new ResponseEntity<String>("{\"message\":\"" + "User logged off successfully" + "\"}", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\"}", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/allCheckInCheckOut",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> fetchAllSessions(@RequestParam("employeeCode") final int employeeCode) {
        ResponseEntity<?> responseEntity;
        List<loginLogoutData> logins = null;
        try {
            logins = userService.getAllCheckInCheckOutData(employeeCode);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<List<loginLogoutData>>(logins,HttpStatus.NOT_FOUND);
        }
        responseEntity = new ResponseEntity<List<loginLogoutData>>(logins, HttpStatus.OK);
        return responseEntity;
    }


    @DeleteMapping(value = "/deleteUser/{employeeCode}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteUserByEmployeeCode(@PathVariable("employeeCode") int employeeCode) {
        ResponseEntity<?> responseEntity;
        try {
            System.out.println("delete");
            userService.deleteSessionAndUserByEmployeeCode(employeeCode);
        } catch (LoginLogoutException e) {
            responseEntity = new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            responseEntity = new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
        }
        responseEntity = new ResponseEntity<String>("{\"message\":\"" + "User has been successfully deleted" + "\"}", HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping(path = "/allUsers",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> fetchAllUsers() {
        ResponseEntity<?> responseEntity;
        List<User> userDetails = null;
        try {
            userDetails = userService.getAllUsers();
        } catch (UserNotFoundException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        responseEntity = new ResponseEntity<List<User>>(userDetails, HttpStatus.OK);
        return responseEntity;
    }

}
