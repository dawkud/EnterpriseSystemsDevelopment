
package com.controllers;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ClaimsService", targetNamespace = "http://ws.esd.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ClaimsService {


    /**
     * 
     * @param userStatus
     * @param dateOfRegistration
     * @param numberOfClaims
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "validateClaim", targetNamespace = "http://ws.esd.com/", className = "com.controllers.ValidateClaim")
    @ResponseWrapper(localName = "validateClaimResponse", targetNamespace = "http://ws.esd.com/", className = "com.controllers.ValidateClaimResponse")
    @Action(input = "http://ws.esd.com/ClaimsService/validateClaimRequest", output = "http://ws.esd.com/ClaimsService/validateClaimResponse")
    public String validateClaim(
        @WebParam(name = "dateOfRegistration", targetNamespace = "")
        String dateOfRegistration,
        @WebParam(name = "numberOfClaims", targetNamespace = "")
        Integer numberOfClaims,
        @WebParam(name = "userStatus", targetNamespace = "")
        String userStatus);

}