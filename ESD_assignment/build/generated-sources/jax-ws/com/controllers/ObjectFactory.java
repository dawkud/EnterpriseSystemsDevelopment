
package com.controllers;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.controllers package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ValidateClaim_QNAME = new QName("http://ws.esd.com/", "validateClaim");
    private final static QName _ValidateClaimResponse_QNAME = new QName("http://ws.esd.com/", "validateClaimResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.controllers
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ValidateClaim }
     * 
     */
    public ValidateClaim createValidateClaim() {
        return new ValidateClaim();
    }

    /**
     * Create an instance of {@link ValidateClaimResponse }
     * 
     */
    public ValidateClaimResponse createValidateClaimResponse() {
        return new ValidateClaimResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateClaim }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.esd.com/", name = "validateClaim")
    public JAXBElement<ValidateClaim> createValidateClaim(ValidateClaim value) {
        return new JAXBElement<ValidateClaim>(_ValidateClaim_QNAME, ValidateClaim.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateClaimResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.esd.com/", name = "validateClaimResponse")
    public JAXBElement<ValidateClaimResponse> createValidateClaimResponse(ValidateClaimResponse value) {
        return new JAXBElement<ValidateClaimResponse>(_ValidateClaimResponse_QNAME, ValidateClaimResponse.class, null, value);
    }

}
