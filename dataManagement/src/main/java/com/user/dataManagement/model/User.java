package com.user.dataManagement.model;


import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class User {

    @Id
    @Column(name = "EMPLOYEE_CODE")
    private int employeeCode;

    private String name;
    private Date dateOfBirth;
    private String password;

    @OneToMany(targetEntity = loginLogoutData.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_EMPLOYEE_CODE", referencedColumnName = "EMPLOYEE_CODE")
    List<loginLogoutData> loginLogoutList ;

    public User() {
    }

    public User(int employeeCode, String name, Date dateOfBirth, String password, List<loginLogoutData> loginLogoutList) {
        this.employeeCode = employeeCode;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.loginLogoutList = loginLogoutList;
    }

    public int getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(int employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<loginLogoutData> getLoginLogoutList() {
        return loginLogoutList;
    }

    public void setLoginLogoutList(List<loginLogoutData> loginLogoutList) {
        this.loginLogoutList = loginLogoutList;
    }
}
