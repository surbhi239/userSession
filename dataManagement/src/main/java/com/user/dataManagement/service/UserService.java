package com.user.dataManagement.service;


import com.user.dataManagement.model.User;

import java.util.List;

public interface UserService {

    boolean addUser(List<User> users);

    User findUserByEmployeeIdAndPassword(String EmployeeId, String password);
}
