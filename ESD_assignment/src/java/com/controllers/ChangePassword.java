/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.models.User;
import com.models.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dkude
 */
@WebServlet(name = "ChangePassword", urlPatterns = {"/ChangePassword"})
public class ChangePassword extends HttpServlet {

    //Variables
    String DASH_MSG = "DASH_MSG";
    String LOGIN_MSG = "LOGIN_MSG";
    String err = "";
    UserDAO users = new UserDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

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

        HttpSession session = request.getSession(false);

        // If session doesn't exist, creates it
        if (session == null) {
            session = request.getSession();
        }
        //redirects to change password page 
        request.getRequestDispatcher("change-password.jsp").forward(request, response);

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

        HttpSession session = request.getSession(false);
        //If session doesn't exist, creates it
        if (session == null) {
            session = request.getSession();
        }
        //Setting up all string variables by getting all the information that user filled in from the page
        String username = (String) session.getAttribute("username");

        request.setAttribute("changePassword", "ogfwrogte");
        request.setAttribute("DASH_MSG", "Change password for user " + username);

        String currentPassword = (String) request.getParameter("currentPassword");
        String newPassword = (String) request.getParameter("newPassword");
        String confirmNewPassword = (String) request.getParameter("confirmNewPassword");
        
        
        //Debugging Log
        System.out.println(currentPassword);
        System.out.println(newPassword);
        System.out.println(confirmNewPassword);

        err = "";
        
        //If system validates correct details of currentPassword, newPassword, confirmNewPassword, username
        //display message about user changed his/her password sucessfully and redirect back to login page
        //else user did not fill all the requirements on the page, displays an error message and redirect
        //back to change password page.
        if (validatePassword(currentPassword, newPassword, confirmNewPassword, username)) {
            System.out.println("Success");
            User tempUser = new User();
            tempUser.setPassword(newPassword);
            tempUser.setUsername(username);
            users.updatePassword(tempUser);
            //response.sendRedirect(request.getContextPath() + "/index.jsp");
            request.setAttribute(LOGIN_MSG, "Password for user " + username + " changed");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            System.out.println("Failure");
            request.setAttribute(DASH_MSG, err);
            request.getRequestDispatcher("change-password.jsp").forward(request, response);
        }
    }

    //private method for validating whether user input correct details to change their password or not
    private boolean validatePassword(String currentPassword, String newPassword, String confirmPassword, String username) {
        /* logic */
        User u = users.getByID(username);

        //check if username was valid
        if (u == null) {
            System.out.println("Invalid username");
            err += "Invalid username\n";
            return false;
        }

        //check if password matches the password in the database
        if (!u.getPassword().equals(currentPassword)) {
            System.out.println("Invalid current password");
            err += "Invalid current password\n";
            return false;
        }

        //check if new passwords match
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match");
            err += "New passwords do not match\n";
            return false;
        }

        //check if newpassword is too long (20 char limit)
        if (newPassword.length() > 20 || confirmPassword.length() > 20) {
            System.out.println("New password is too long (max 20)");
            err += "New password is too long (max 20)\n";
            return false;
        }
        //check if new password equals to the old one
        if (newPassword.equals(u.getPassword())) {
            err += "Old and new passwords are the same";
            return false;
        }

        //maybe only utf-8 characters?
        return true;
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
