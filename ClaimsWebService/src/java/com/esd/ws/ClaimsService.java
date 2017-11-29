/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esd.ws;

import java.sql.Date;
import java.util.Calendar;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author dkude
 */
@WebService(serviceName = "ClaimsService")
@Stateless()
public class ClaimsService {

    /**
     * This is a sample web service operation
     */
@WebMethod(operationName = "validateClaim")
    public String validateClaim(@WebParam(name = "dateOfRegistration") String dateOfRegistration, @WebParam(name = "numberOfClaims") Integer numberOfClaims, @WebParam(name = "userStatus") String userStatus) {
        //TODO write your implementation code here:
        
        /* Validate number of claims already made */
        if(numberOfClaims > 2) {
            return "ineligible for support, claims made " + numberOfClaims + ", max is 2";
        }
        
        /* validate date of registration */
//        Date Currentdate = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        //if dor > 6months from now
        Date dor = Date.valueOf(dateOfRegistration);
        if(cal.getTimeInMillis() - dor.getTime() < 0) {
            return "ineligible for support, date of registration: " + dateOfRegistration.toString() + ", can only make claims after six months";
        }
        
        if(userStatus.equals("Suspended")){
            return "ineligible for support, suspended users cannot make claims";
        }
        
        if(userStatus.equals("Applied")) {
            return "ineligible for support, probational users cannot make claims";
        }
        
        return "eligible for support";
    }
}
