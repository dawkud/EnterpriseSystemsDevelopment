/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.models.LoginModel;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    //Message of user entered wrong details
    public static String ERR_MSG = "ERR_MSG";

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
        //Get into main page of the serlvet
        request.getRequestDispatcher("index.jsp").forward(request, response);
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

        // If first connection, create session, if re-loggin in then invalidate and re-create
        // Prevents being able to bypass filters i.e. member accessing admin area
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
        } else {
            session.invalidate();
            session = null;
        }

        //variables 
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LoginModel lm = new LoginModel(username, password);

        //validates user from login model, if user information entered correctly, redirect
        //to the correct dashboard by status, else display error page and message for incorrect login details
        if (lm.validateUser(username, password) == true) {
            if (session == null) {
                session = request.getSession();
            }
            //set attributes into session
            session.setAttribute("user", lm.getStatus().trim());
            session.setAttribute("username", lm.getUsername().trim());
            //if the system found user's status is admin, redirect the page to admin dashboard
            //else redirect it to member dashboard,
            //
            if (lm.getStatus().trim().equalsIgnoreCase("ADMIN")) {
                //request.getRequestDispatcher("/WEB-INF/admin-dashboard.jsp").forward(request, response);
                response.sendRedirect(request.getContextPath() + "/admin-dashboard.jsp");
                System.out.println(request.getContextPath());
            } else {
                //request.getRequestDispatcher("/WEB-INF/member-dashboard.jsp").forward(request, response);
                response.sendRedirect(request.getContextPath() + "/member-dashboard.jsp");
            }
        } else {
            request.setAttribute(ERR_MSG, "Username or password was incorrect");
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
