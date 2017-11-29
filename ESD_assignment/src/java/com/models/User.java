/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models;

import java.sql.Date;

/**
 *
 * @author d2-kuder
 */
public class User {

    // constant to reduce duplication and spelling/coding errors
    public static final String APPLIED = "Applied";
    public static final String APPROVED = "Approved";
    public static final String ADMIN = "Admin";
    public static final String SUSPENDED = "Suspended";

    // variable
    private String firstName;
    private String lastName;
    private Date dob; //Date of birth
    private Date dor; //Date of registration
    private double balance = 0;
    private String username;
    private String password;
    private String address;
    private String status;
 
    // Constructure
    public User() {
        
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDor() {
        return this.dor;
    }

    public void setDor(Date dor) {
        this.dor = dor;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" + "firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", dor=" + dor + ", balance=" + balance + ", username=" + username + ", password=" + password + ", address=" + address + ", status=" + status + '}';
    }

}
