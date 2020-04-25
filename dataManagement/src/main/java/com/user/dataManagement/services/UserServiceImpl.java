package com.user.dataManagement.services;

import com.user.dataManagement.exception.LoginLogoutException;
import com.user.dataManagement.exception.UserAlreadyExistsException;
import com.user.dataManagement.exception.UserNotFoundException;
import com.user.dataManagement.model.*;
import com.user.dataManagement.repository.LoginLogoutRepository;
import com.user.dataManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final transient UserRepository userRepository;
    private final transient LoginLogoutRepository loginLogoutRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, LoginLogoutRepository loginLogoutRepository) {
        super();
        this.userRepository = userRepository;
        this.loginLogoutRepository = loginLogoutRepository;
    }

    @Override
    public boolean saveUser(User userDetails) throws UserAlreadyExistsException {
        Optional<User> ud = userRepository.findById(userDetails.getEmployeeCode());
        if (ud.isPresent()) {
            throw new UserAlreadyExistsException("User with email already exist");
        }
        User user = new User();
        user.setEmployeeCode(userDetails.getEmployeeCode());
        user.setName(userDetails.getName());
        user.setDateOfBirth(userDetails.getDateOfBirth());
        user.setPassword(userDetails.getPassword());
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean findByEmpCodeAndPassword(int empCode, String password) throws UserNotFoundException {
        Optional<User> optionalUserDetails = userRepository.findByEmployeeCodeAndPassword(empCode, password);
        User userDetails;
        if(optionalUserDetails.isPresent()) {
            userDetails = optionalUserDetails.get();
        } else {
            throw new UserNotFoundException("User id not found in database");
        }
        loginLogoutData ld = new loginLogoutData();
        ld.setCheckInDate(new Date());
        ld.setEmpId(userDetails.getEmployeeCode());
        loginLogoutRepository.save(ld);
        return true;
    }

    @Override
    public boolean logoutUser(int empCode)throws UserNotFoundException{
        Optional<User> optionalUserDetails = userRepository.findById(empCode);
        User userDetails;
        System.out.println(optionalUserDetails.get());
        if(optionalUserDetails.isPresent()) {
            userDetails = optionalUserDetails.get();
        } else {
            throw new UserNotFoundException("User id not found in database");
        }
        List<loginLogoutData> lld = loginLogoutRepository.findByEmpId(userDetails.getEmployeeCode());
        if(lld != null)
        {
            for(int i = 0; i < lld.size(); i++){
                if(lld.get(i).getCheckInDate() != null){
                    lld.get(i).setCheckOutDate(new Date());
                    loginLogoutRepository.save(lld.get(i));
                }
            }
        }
        return true;
    }

    @Override
    public List<loginLogoutData> getAllCheckInCheckOutData(int employeeCode){
        final List<loginLogoutData> loginData= loginLogoutRepository.findByEmpId(employeeCode);
        return loginData;
    }

    @Override
    public boolean deleteSessionAndUserByEmployeeCode(final int employeeCode) throws LoginLogoutException, UserNotFoundException {

        final List<loginLogoutData> ld = loginLogoutRepository.findByEmpId(employeeCode);
        if (ld == null) {
            throw new LoginLogoutException("Can't delete transaction, No details found");
        }
        for(int i=0;i< ld.size();i++ )
            loginLogoutRepository.delete(ld.get(i));

        Optional<User> user= userRepository.findByEmployeeCode(employeeCode);
        if(!user.isPresent())
            throw new UserNotFoundException("Unable to find user");
        userRepository.delete(user.get());
        return true;
    }
    @Override
    public Optional<User> getByEmployeeId(int employeeCode) throws UserNotFoundException {
        Optional<User> userDetails = userRepository.findById(employeeCode);
        if (userDetails == null) {
            throw new UserNotFoundException("User not found");
        }
        return userDetails;
    }
    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        final List<User> userDetails = userRepository.findAll();
        if (userDetails == null) {
            throw new UserNotFoundException("User not found");
        }
        return userDetails;
    }
}
