/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.models.AddressBean;
import com.models.RegModel;
import com.models.User;
import com.services.AddressAPI;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author d2-kuder
 */
@WebServlet(name = "Registration", urlPatterns = {"/Registration"})
public class RegistrationServlet extends HttpServlet {

    public static String LOGIN_MSG = "LOGIN_MSG";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        request.getRequestDispatcher("registration.jsp").forward(request, response);

        HttpSession session = request.getSession(false);

        // If session doesn't exist, create it
        if (session == null) {
            session = request.getSession();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        /**
         * Read input from registration.jsp *
         */
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Date dob = Date.valueOf(request.getParameter("dob"));

        String button = request.getParameter("submit"); // Reads the submit button from the JSP and casts it to String button
        ArrayList<AddressBean> addresses = null;
        
        switch (button) {
            case "resolvePostcode": // Checks the postcode input against the Address API
                String postcode = request.getParameter("postcode");
                AddressAPI addressAPI = new AddressAPI(postcode);
                addresses = addressAPI.findAddress();

                // If no addresses found for the postcode then proceed to manual entry
                if (addresses.isEmpty()) {
                    System.out.println("No addresses found");
                    request.setAttribute("manualAddress", true);
                    System.out.println(request.getAttribute("manualAddress"));
                }                 
                
                // Save the previous input to avoid resetting once the check is done against postcode
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("addresses", addresses);
                request.setAttribute("dob", dob);
                request.getRequestDispatcher("registration.jsp").forward(request, response);
                break;

            case "generateUser": // Generate user from information provided
                HttpSession session = request.getSession(false);
                String address = request.getParameter("address");

                // Registration is completed using the RegModel
                RegModel regModel = new RegModel(firstName, lastName, dob, address);
                User user = regModel.generateUser();

                String loginMsg = "You have successfully registered with XYZ Drivers Association!\n"
                        + "You have been registered as a provisional member, pending membership fee payment and approval.\n"
                        + "Your username is: " + user.getUsername() + " and your password is: " + user.getPassword();

                request.setAttribute(LOGIN_MSG, loginMsg);
                request.getRequestDispatcher("login.jsp").forward(request, response);

                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
