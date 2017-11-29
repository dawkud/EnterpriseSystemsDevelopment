/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models;

/**
 *
 * @author Prithpal Sooriya
 */
public class LoginModel {

    //Variables
    private User user;

    //Constructor
    public LoginModel(String username, String password) {
        Database db = new Database();

        //cast object returned into User
        user = (User) db.searchData(Database.USERS_TABLE, Database.USERS_TABLE_ID, username);
    }

    //Having a boolean to validate user inputted details, if the system detects user input, check
    //if both username and password entered or not, then if both of them can be searched in
    //Database, returns to true, otherwise return to false to the system.
    public boolean validateUser(String username, String password) {
        if(user != null){
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
            return false;
        }
        
        return false;
    }

//    public User getUser() {
//        return user;
//    }
    
    /*Getters method with String*/
    public String getUsername() {
        return user.getUsername();
    }
    
    public String getPassword() {
        return user.getPassword();
    }
    
    public String getStatus() {
        System.out.println("Status: " + user.getStatus());
        if (user.getStatus().trim().equalsIgnoreCase("ADMIN")){
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
        return user.getStatus();
    }
    
    
}