package com.models;

import java.sql.Date;
import java.util.UUID;

public class RegModel {

    String username, password;
    String firstName;
    String lastName;
    Date dob;
    String address;

    public RegModel(String firstName, String lastName, Date dob, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.username = generateUsername();
        this.password = generatePassword();
    }

    private String generateUsername() {          // Generates a username based off the first letter of the forename and the surname seperated by a hyphen.
        String username = (firstName.trim().charAt(0) + "-" + lastName.trim()).toLowerCase();
        return username;
    }

    private String generatePassword() {          // Generates a random password 6 characters long
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public User generateUser() {            // Generates a user using the fields below.
        User user = new User();
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setAddress(this.address);
        user.setDob(this.dob);
        user.setDor(new Date(System.currentTimeMillis())); // Date of registration
        user.setStatus(User.APPLIED);                      // Sets the users as applied
        user.setBalance(10);                               // Applies a a £10 fee as they are newly registered and have to pay the sign up fee.

        Database db = new Database();

        // Username as ID for both
        db.insertDataIntoMembers(this.username, this.firstName, this.lastName, this.address, this.dob, user.getDor(), user.getStatus(), user.getBalance());
        db.insertDataIntoUsers(this.username, this.password, user.getStatus());
        return user;
    }

}
