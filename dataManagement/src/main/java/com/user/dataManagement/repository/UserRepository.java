package com.user.dataManagement.repository;

import com.user.dataManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmployeeCode(int employeeCode);
    Optional<User> findByEmployeeCodeAndPassword(int employeeCode, String password);
}
