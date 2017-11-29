package com.models;

import java.sql.Date;

public class ClaimsBean {

    // constant to reduce duplication and spelling/coding errors
    public static final String APPROVED = "Approved";
    public static final String SUBMITTED = "Submitted";
    public static final String REJECTED = "Rejected";

    // variable
    private int ID;
    private String memberID;
    private Date date;
    private String state;         // Whether the claim has been approved etc
    private double amount;
    private String description;   // Reason for claim
    private boolean accepted;     // True = accepted || False = Rejected

    // constructor
    public ClaimsBean() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Claim [ID = " + ID + ", Date = " + date + ", Description = " + description + ", Status = " + state + ", amount = " + amount + "]";
    }
}
