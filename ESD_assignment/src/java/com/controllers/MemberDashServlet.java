/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.esd.ws.ClaimsService_Service;
import com.models.ClaimsBean;
import com.models.ClaimsDAO;
import com.models.MembersDAO;
import com.models.PaymentBean;
import com.models.PaymentDAO;
import com.models.User;
import com.models.UserDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author dkude
 */
@WebServlet(name = "MemberDashServlet", urlPatterns = {"/MemberDashServlet"})
public class MemberDashServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/ClaimsService/ClaimsService.wsdl")
    private ClaimsService_Service service;

    MembersDAO members = new MembersDAO();
    UserDAO users = new UserDAO();
    ClaimsDAO claims = new ClaimsDAO();
    PaymentDAO payments = new PaymentDAO();
    String DASH_MSG = "DASH_MSG";
    DecimalFormat df = new DecimalFormat("#.##");

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

        // If session doesn't exist, create it
        if (session == null) {
            session = request.getSession();
        }

        request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
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
        if (session == null) {
            session = request.getSession();
        }

        String username = (String) session.getAttribute("username"); // Retrieve the user from session

        /**
         * Read input from member-dashboard.jsp *
         */
        String button = request.getParameter("function"); // Reads the function group from the JSP and casts it to String button

        User tempUser = members.getByID(username);

        switch (button) {
            case "checkOutstandingBalance": // Allows member to check outstanding balance
                request.setAttribute("checkOutstandingBalance", tempUser.getBalance());
                double outstandingBalance = tempUser.getBalance();
                request.setAttribute("outstandingBalance", outstandingBalance);
                request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "makePayment": // Allows the user to make a payment against outstanding balance
                double currentBalance = tempUser.getBalance();

                try {
                    double paymentAmount = Double.parseDouble(df.format(Double.parseDouble(request.getParameter("paymentAmount"))));

                    System.out.println("Payment amount: £" + paymentAmount);
                    System.out.println("balance: " + currentBalance);
                    if (paymentAmount > currentBalance) { // Do some payment validation
                        request.setAttribute(DASH_MSG, "Payment amount too high");
                    } else if (paymentAmount <= 0) {
                        request.setAttribute(DASH_MSG, "Payment amount too low");
                    } else {
                        // Payment successful
                        double newBalance = Double.parseDouble(df.format(currentBalance - paymentAmount));
                        tempUser.setBalance(newBalance);
                        members.updateBalance(tempUser);

                        // Add payment
                        PaymentBean payment = new PaymentBean();
                        payment.setAmount(paymentAmount);
                        payment.setDate(new Date(System.currentTimeMillis()));
                        payment.setTimestamp(new Time(System.currentTimeMillis()));
                        payment.setMemberID(username);
                        payment.setType("Balance payment");

                        payments.create(payment);

                        request.setAttribute(DASH_MSG, "Payment of £" + paymentAmount + " made to " + username);
                        request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                    }

                } catch (NumberFormatException e) {
                    request.setAttribute(DASH_MSG, "Input was not a number");
                    e.printStackTrace();
                }

                request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "submitClaim": // Redirect user to the claim section
                request.setAttribute("submitClaim", "asd");
                request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "submitAClaim": // Allows the user to submit a claim
                int numberOfClaims = 0;
                // Checks if claims were made in the past year by the user and counts them if true
                for (Object object : claims.getAllByMemberID(username)) {
                    ClaimsBean claim = (ClaimsBean) object;
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.YEAR, -1);
                    if (cal.getTimeInMillis() - claim.getDate().getTime() < 0) {
                        //valid claim
                        numberOfClaims++;
                    }
                }
                // Gets user status to complete eligibility check
                String status = tempUser.getStatus().trim();
                Double balance = tempUser.getBalance();
                Date dor = tempUser.getDor();
                
                if (status.equals(User.APPROVED)) {
                    if (balance == 0) {
                        // If user is 'Approved' & balance is paid off send of to WebService to check eligibility
                        String str = validateClaim(dor.toString(), numberOfClaims, status);
                        // Checks the WebService response and proceeds, informs user if they're eligible for support or not
                        if (str.contains("Ineligible") || str.contains("ineligible")) {
                            ClaimsBean claim = new ClaimsBean();
                            claim.setAmount(Double.parseDouble(df.format(Double.parseDouble(request.getParameter("claimAmount")))));
                            claim.setDate(new Date(System.currentTimeMillis()));
                            claim.setDescription(request.getParameter("claimDescription"));
                            claim.setMemberID(username);
                            claim.setState(ClaimsBean.SUBMITTED);
                            claims.create(claim);
                            request.setAttribute(DASH_MSG, "Claim of £" + claim.getAmount() + " has been submitted, you are " + str);
                        } else {
                            ClaimsBean claim = new ClaimsBean();
                            claim.setAmount(Double.parseDouble(df.format(Double.parseDouble(request.getParameter("claimAmount")))));
                            claim.setDate(new Date(System.currentTimeMillis()));
                            claim.setDescription(request.getParameter("claimDescription"));
                            claim.setMemberID(username);
                            claim.setState(ClaimsBean.SUBMITTED);
                            claims.create(claim);
                            request.setAttribute(DASH_MSG, "Claim of £" + claim.getAmount() + " has been submitted, awaiting approval, you are " + str);
                        }
                    } else { // If outstanding balance is greater than zero
                        request.setAttribute(DASH_MSG, "You can only make claims when you've paid your outstanding balance £" + balance);
                    }
                } else {
                    // If member is not 'Approved' then reject their claim
                    request.setAttribute(DASH_MSG, "Only Approved members can make claims");
                }
                request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "listPayments": // List payments and claims for the member

                //set all claims from this member
                if (claims.getAllByMemberID((String) session.getAttribute("username")).size() > 0) {
                    System.out.println("claims found");

                }
                request.setAttribute("memberClaims", claims.getAllByMemberID(username));

                //set all payments form this member
                request.setAttribute("listPayments", payments.getAllByMemberId(username));

                request.setAttribute("DASH_MSG", "All claims and payments for user " + username);
                request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
                break;

            case "changePassword": // Allows the member to change their password
                request.setAttribute("DASH_MSG", "Change password for user " + username);
                request.getRequestDispatcher("change-password.jsp").forward(request, response);
                break;
        }

//        request.getRequestDispatcher("member-dashboard.jsp").forward(request, response);
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

    private String validateClaim(java.lang.String dateOfRegistration, java.lang.Integer numberOfClaims, java.lang.String userStatus) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.esd.ws.ClaimsService port = service.getClaimsServicePort();
        return port.validateClaim(dateOfRegistration, numberOfClaims, userStatus);
    }

}
