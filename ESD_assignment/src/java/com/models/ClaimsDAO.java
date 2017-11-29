/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models;

import com.sun.faces.util.CollectionsUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Prithpal Sooriya
 *
 * This will handle the Claims bean/POJO and its table relations in the database
 * implements GenericDAO, and uses generics Claimsbean (the data we are accessing) and
 * Integer (the ID for the ClaimsBean)
 *
 */
public class ClaimsDAO implements GenericDAO<ClaimsBean, Integer> {

    //used to access the database/JDBC
    private Database db;

    //constructor to initialise the database
    public ClaimsDAO() {
        db = new Database();
    }

    //Adds a new ClaimsBean into the database
    //requires a ClaimsBean parameter
    @Override
    public boolean create(ClaimsBean newClaim) {
        boolean inserted = db.insertData(Database.CLAIMS_TABLE, newClaim);
        return inserted;
    }

    //list all claims
    @Override
    public List<ClaimsBean> getAll() {
        ArrayList<ClaimsBean> claims = new ArrayList<>();

        //for each claim in Claims table
        for (Object object : db.getAllData(Database.CLAIMS_TABLE)) {
            ClaimsBean claim = (ClaimsBean) object;
            claims.add(claim);
        }

        return claims;
    }

    //lists all claims with the same status given (as a parameter)
    public List<ClaimsBean> getAllByStatus(String status) {
        ArrayList<ClaimsBean> claims = new ArrayList<>();

        //for each claim in Claims table
        for (Object object : db.getAllData(Database.CLAIMS_TABLE, Database.CLAIMS_TABLE_STATUS, status)) {
            ClaimsBean claim = (ClaimsBean) object;
            claims.add(claim);
        }

        return claims;
    }

    //lists all claims with the same memberId given (as a parameter)
    public List<ClaimsBean> getAllByMemberID(String memberId) {
        ArrayList<ClaimsBean> claims = new ArrayList<>();

        //for each claim in Claims table
        for (Object object : db.getAllData(Database.CLAIMS_TABLE, Database.CLAIMS_TABLE_MEMBER_ID, memberId)) {
            ClaimsBean claim = (ClaimsBean) object;
            claims.add(claim);
        }

        return claims;
    }

    //lists the annual turnover of all outstanding claims
    public double annualTurnoverDouble() {
        ArrayList<ClaimsBean> claims = new ArrayList<>();
        double total = 0;
        //approved claims only
        for (Object object : db.getAllData(Database.CLAIMS_TABLE, Database.CLAIMS_TABLE_STATUS, ClaimsBean.APPROVED)) {
            ClaimsBean claim = (ClaimsBean) object;

            //add claims from past year-today
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            if (cal.getTimeInMillis() - claim.getDate().getTime() <= 0) {
                claims.add(claim);
                total += claim.getAmount();

            }
        }
        return total;
    }

    //lists all claims that are used for the annual turnover
    public List<ClaimsBean> annualTurnoverList() {
        ArrayList<ClaimsBean> claims = new ArrayList<>();
        double total = 0;
        //approved claims only
        for (Object object : db.getAllData(Database.CLAIMS_TABLE, Database.CLAIMS_TABLE_STATUS, ClaimsBean.APPROVED)) {
            ClaimsBean claim = (ClaimsBean) object;

            //add claims from past year-today
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            if (cal.getTimeInMillis() - claim.getDate().getTime() <= 0) {
                claims.add(claim);
                total += claim.getAmount();

            }
        }
        return claims;
    }

    //returns a ClaimsBean by the id given as a parameter
    @Override
    public ClaimsBean getByID(Integer id) {
        ClaimsBean claim = (ClaimsBean) db.searchData(Database.CLAIMS_TABLE, Database.CLAIMS_TABLE_ID, id);
        if (claim == null) {
            System.out.println("ClaimsDAO.getByID(): Claim not found");
        }
        //other classes need to careful to see if this is null or not.
        return claim;
    }

    //updates the status of a given claim
    //requires that the ClaimsBean given (as a parameter) has an ID and a status
    public boolean updateStatus(ClaimsBean updatedClaim) {
        //this will update ONLY the user table. Status will also needed to be changed in members table
        return db.updateData(Database.CLAIMS_TABLE, updatedClaim.getID(), Database.CLAIMS_TABLE_STATUS, updatedClaim.getState());
    }

    //updates the status of a given claim
    //requires that the ClaimsBean given (as a parameter) has an ID a description
    public boolean updateDescription(ClaimsBean updatedClaim) {
        //this will update ONLY the user table. Status will also needed to be changed in members table
        return db.updateData(Database.CLAIMS_TABLE, updatedClaim.getID(), Database.CLAIMS_TABLE_STATUS, updatedClaim.getDescription());
    }

}
