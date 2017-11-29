/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Prithpal Sooriya
 *
 * UsersDAO
 *
 * This will handle the User bean/POJO and its table relations in the database
 * implements GenericDAO, and uses generics User (the data we are accessing) and
 * String (the ID for the User)
 *
 * NOTE: the User table in the database will only populate the username,
 *       password, and status of the User class.
 */
public class UserDAO implements GenericDAO<User, String> {

    //variable used for accessing the database
    private Database db;

    //constructor, mainly used for initialising the database variable
    public UserDAO() {
        db = new Database();
    }

    //Add a User into the database
    //requires a User as a parameter.
    @Override
    public boolean create(User newUser) {
        boolean inserted = db.insertData(Database.USERS_TABLE, newUser);

        return inserted;
    }

    //List all users from the database
    @Override
    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();

        //users from user table
        for (Object object : db.getAllData(Database.USERS_TABLE)) {
            User user = (User) object;
            users.add(user);
        }

        return users;
    }

    //Return all users from the User's table where the status is the same as the one given
    //Requires String status as a parameter.
    public List<User> getAllByStatus(String status) {
        ArrayList<User> users = new ArrayList<>();

        //users from user table
        for (Object object : db.getAllData(Database.USERS_TABLE, Database.USERS_TABLE_STATUS, status)) {
            User user = (User) object;
            users.add(user);
        }

        return users;
    }


    //Returns a User from the ID given
    @Override
    public User getByID(String id) {

        User user = (User) db.searchData(Database.USERS_TABLE, Database.USERS_TABLE_ID, id);
        if(user == null) {
            System.out.println("UserDAO.getByID(): User not found");
        }
        //other classes need to careful to see if this is null or not.
        return user;
    }

    //Update: updates a user from their given password
    //NOTE: the parameter is a User, and so this User must have an ID and password
    public boolean updatePassword(User updatedUser) {

        return db.updateData(Database.USERS_TABLE, updatedUser.getUsername(), Database.USERS_TABLE_PASSWORD, updatedUser.getPassword());

    }

    //Update: updates user from their new Status
    //NOTE: the parameter is a user, and so this User must have an ID and status
    public boolean updateStatus (User updatedUser) {

        //this will update ONLY the user table. Status will also needed to be changed in members table
        return db.updateData(Database.USERS_TABLE, updatedUser.getUsername(), Database.USERS_TABLE_STATUS, updatedUser.getStatus());

    }

}
