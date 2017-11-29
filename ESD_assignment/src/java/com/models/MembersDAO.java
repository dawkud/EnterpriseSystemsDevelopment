/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Prithpal Sooriya
 *
 * This will handle the User bean/POJO and its table relations in the database
 * implements GenericDAO, and uses generics User (the data we are accessing) and
 * String (the ID for the User)
 *
 * NOTE: User Bean/POJO is used for the MemberDAO and UserDAO, as tables are related!
 * NOTE: the Member table in the database will only populate the everything apart from
 *       user password, which is only found in the user table
 */
public class MembersDAO implements GenericDAO<User, String> {

    //private variable to access the database (JDBC)
    private Database db;

    //constructor
    public MembersDAO() {
        db = new Database();
    }

    //Creates a new Member in the members table, will require a User as a parameter
    @Override
    public boolean create(User newMember) {
        boolean inserted = db.insertData(Database.MEMBERS_TABLE, newMember);
        return inserted;
    }

    //gets all members from members table
    @Override
    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();

        //for each member in Members table
        for (Object object : db.getAllData(Database.MEMBERS_TABLE)) {
            User user = (User) object;
            users.add(user);
        }

        return users;
    }

    //gets all members from members table (that have matching status given)
    public List<User> getAllByStatus(String status) {
        ArrayList<User> users = new ArrayList<>();

        //for each member in Members table
        for (Object object : db.getAllData(Database.MEMBERS_TABLE, Database.MEMBERS_TABLE_STATUS, status)) {
            User user = (User) object;
            users.add(user);
        }

        return users;
    }

    //lists all users who have a balance greater than 0
    public List<User> listBalances() {
        ArrayList<User> users = new ArrayList<>();
        for (Object object : db.getAllData(Database.MEMBERS_TABLE)) {
            User user = (User) object;
           if (user.getBalance() > 0) {
                users.add(user);
           }
        }
        return users;
    }

    //Get a single member from their ID (username)
    @Override
    public User getByID(String id) {
        User user = (User) db.searchData(Database.MEMBERS_TABLE, Database.MEMBERS_TABLE_ID, id);
        if (user == null) {
            System.out.println("MemberDAO.getByID(): User not found");
        }
        //other classes need to careful to see if this is null or not.
        return user;
    }

    //Updates the status of the user given
    //requires that the User parameter is given has a username and a status
    public boolean updateStatus(User updatedUser) {

        //this will update ONLY the user table. Status will also needed to be changed in members table
        return db.updateData(Database.MEMBERS_TABLE, updatedUser.getUsername(), Database.MEMBERS_TABLE_STATUS, updatedUser.getStatus());

    }

    //Updates the balance of user in table
    //reqiures that the User parameter is given a username and a balance
    public boolean updateBalance(User updatedUser) {
        //this will update ONLY the user table. Status will also needed to be changed in members table
        return db.updateData(Database.MEMBERS_TABLE, updatedUser.getUsername(), Database.MEMBERS_TABLE_BALANCE, updatedUser.getBalance());
    }

}
