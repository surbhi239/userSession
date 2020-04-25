package com.user.dataManagement.services;

import com.user.dataManagement.exception.LoginLogoutException;
import com.user.dataManagement.exception.UserAlreadyExistsException;
import com.user.dataManagement.exception.UserNotFoundException;
import com.user.dataManagement.model.User;
import com.user.dataManagement.model.loginLogoutData;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean saveUser(User userDetails) throws UserAlreadyExistsException;

    public boolean findByEmpCodeAndPassword(int email, String password) throws UserNotFoundException;

    boolean logoutUser(int empCode)throws UserNotFoundException;


    List<loginLogoutData> getAllCheckInCheckOutData(int employeeCode);

    boolean deleteSessionAndUserByEmployeeCode(int employeeCode) throws LoginLogoutException, UserNotFoundException;

    Optional<User> getByEmployeeId(int employeeCode) throws UserNotFoundException;
    List<User> getAllUsers() throws UserNotFoundException;
}
