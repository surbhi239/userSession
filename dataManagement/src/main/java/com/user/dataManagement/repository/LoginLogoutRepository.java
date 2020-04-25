package com.user.dataManagement.repository;

import com.user.dataManagement.model.loginLogoutData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginLogoutRepository extends JpaRepository<loginLogoutData, Integer> {
    List<loginLogoutData> findByEmpId(int id);
}
