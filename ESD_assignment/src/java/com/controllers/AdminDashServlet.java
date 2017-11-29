/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.esd.ws.ClaimsService_Service;
import com.models.ClaimsBean;
import com.models.ClaimsDAO;
import com.models.Database;
import com.models.MembersDAO;
import com.models.PaymentDAO;
import com.models.User;
import com.models.UserDAO;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author DAWID
 */
@WebServlet(name = "AdminDashServlet", urlPatterns = {"/admin-dashboard"})
public class AdminDashServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/ClaimsService/ClaimsService.wsdl") // Reference to WS for checking support eligiblity
    private ClaimsService_Service service;

    // Global parameters
    Database db = new Database();
    MembersDAO members = new MembersDAO();
    UserDAO users = new UserDAO();
    User user = new User();
    ClaimsDAO claims = new ClaimsDAO();
    PaymentDAO payments = new PaymentDAO();
    Calendar cal;
    DecimalFormat df = new DecimalFormat("#.##");

    double membershipFee = 0;
    double claimsDouble = 0;
    int usersInt = 0;

    public static String DASH_MSG = "DASH_MSG"; // Frontend dashboard message

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

        request.getRequestDispatcher("admin-dashboard").forward(request, response);
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

        /**
         * Read input from admin-dashboard.jsp *
         */
        String button = request.getParameter("function"); // Reads the function group from the JSP and casts it to String button

        switch (button) {
            case "listMembers": // List all members
                request.setAttribute("DASH_MSG", "All members");
                request.setAttribute("listMembers", members.getAll());
                break;

            case "listOutstandingBalances": // List balances greater than zero
                request.setAttribute("DASH_MSG", "All outstanding balances");
                request.setAttribute("listOutstandingBalances", members.listBalances());
                break;

            case "listClaims": // List all claims
                request.setAttribute("DASH_MSG", "All claims");
                request.setAttribute("listClaims", claims.getAll());
                break;

            case "listOutstandingClaims": // List claims for the past year which are unapproved
                request.setAttribute("DASH_MSG", "All outstanding claims for the past year");
                ArrayList<ClaimsBean> claimsArr = new ArrayList<>();
                //get all members from listed claims
                ArrayList<User> usersArr = new ArrayList<>();
                ArrayList<User> usersEligible = new ArrayList<>();
                ArrayList<User> usersIneligible = new ArrayList<>();
                Calendar cal = Calendar.getInstance();

                cal.add(Calendar.YEAR, -1); // Take away one year from this year
                for (ClaimsBean claimsBean : claims.getAll()) {
                    if (claimsBean.getState().equals("Approved") || claimsBean.getState().equals("Submitted")) { // Gets all approved and submitted claims
                        if (cal.getTimeInMillis() - claimsBean.getDate().getTime() < 0) {
                            if (claimsBean.getState().equals("Submitted")) { // If claim is outstanding, add it to the array
                                claimsArr.add(claimsBean);
                            }
                            User temp = members.getByID(claimsBean.getMemberID());
                            if (!usersArr.contains(temp)) {
                                usersArr.add(temp);
                            }
                        }
                    }
                }

                int counter[] = new int[usersArr.size()]; // Counter used for support checking purposes
                for (int i = 0; i < counter.length; i++) {
                    counter[i] = 0;
                }
                User temp = null;
                User temp1 = null;
                for (ClaimsBean claimsBean : claimsArr) {

                    for (User user1 : usersArr) {
                        if (claimsBean.getMemberID().equals(user1.getUsername())) {
                            temp = new User();
                            temp.setUsername(user1.getUsername());
                            temp.setDor(user1.getDor());
                            temp.setStatus(user1.getStatus());
                            temp1 = user1;
                            break;
                        }
                    }
                    if (temp != null) {
                        int index = usersArr.indexOf(temp1);

                        counter[index]++; // All 'approved' and 'submitted' claims of a user are counted
                        String str = validateClaim(temp1.getDor().toString(), counter[index], temp1.getStatus().trim()); // WebService checks eigibility for support
                        if (str.contains("Ineligible") || str.contains("ineligible")) {
                            if (!usersIneligible.contains(temp1)) {
                                //add user into ineligible
                                usersIneligible.add(temp1);
                                //remove user from eligible
                                usersEligible.remove(temp1);
                            }
                        } else {
                            if (!usersEligible.contains(temp1)) {
                                usersEligible.add(temp1);
                            }
                        }

                    }
                }
                request.setAttribute("listOutstandingClaims", claimsArr);
                request.setAttribute("eligibleMembers", usersEligible);
                request.setAttribute("ineligibleMembers", usersIneligible);

                System.out.println("usersIneligible: " + usersIneligible);
                System.out.println("usersEligible: " + usersEligible);
                System.out.println("claimsArr" + claimsArr);
                request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
                break;

            case "listProvisionalMembers": // List 'Applied' users
                request.setAttribute("DASH_MSG", "All provisional members");
                List<User> membersarr = members.getAllByStatus("Applied"); // Filtering in servlet to avoid doing so in JSP

                request.setAttribute("listProvisionalMembers", membersarr);
                break;

            case "approveClaim": // Claim approval
                String approveClaim = request.getParameter("approveClaim");
                request.setAttribute("DASH_MSG", "Claim " + approveClaim + " approved");

                if (approveClaim != null) {
                    ClaimsBean tempClaim = new ClaimsBean();
                    tempClaim.setID(Integer.parseInt(approveClaim));
                    tempClaim.setState(ClaimsBean.APPROVED);
                    claims.updateStatus(tempClaim);
                }
                break;

            case "rejectClaim": // Reject a claim
                String rejectClaim = request.getParameter("rejectClaim");
                request.setAttribute("DASH_MSG", "Claim " + rejectClaim + " rejected");

                if (rejectClaim != null) {
                    ClaimsBean tempClaim = new ClaimsBean();
                    tempClaim.setID(Integer.parseInt(rejectClaim));
                    System.out.println("Claim: " + tempClaim);
                    tempClaim.setState(ClaimsBean.REJECTED);
                    claims.updateStatus(tempClaim);
                }

                break;

            case "resumeMembership": // Resume a user's membership
                String resumeMembership = request.getParameter("resumeMembership");
                request.setAttribute("DASH_MSG", "Membership for " + resumeMembership + " resumed");
                System.out.println("uID: " + resumeMembership);

                if (resumeMembership != null) {
                    User tempUser = new User();
                    tempUser.setUsername(resumeMembership);
                    tempUser.setStatus(User.APPLIED);
                    members.updateStatus(tempUser);
                    users.updateStatus(tempUser);
                }

                break;

            case "approveMember": // Approve an 'Applied' user
                String approveMember = request.getParameter("approveMember");
                request.setAttribute("DASH_MSG", "Member " + approveMember + " approved");
                System.out.println("uID: " + approveMember);

                if (approveMember != null) {
                    User tempUser = new User();
                    tempUser.setUsername(approveMember);
                    tempUser.setStatus(User.APPROVED);
                    members.updateStatus(tempUser);
                    users.updateStatus(tempUser);
                }

                break;

            case "annualTurnover": // Displays financial statistics for the past year
                request.setAttribute("DASH_MSG", "Annual financial statistics");
                request.setAttribute("annualTurnover", claims.annualTurnoverList());
                request.setAttribute("claimTotal", claims.annualTurnoverDouble());
                request.setAttribute("annualPayments", payments.annualTurnoverList());
                request.setAttribute("paymentsTotal", payments.annualTurnoverDouble());

                claimsDouble = 0;
                usersInt = members.getAllByStatus("Approved").size(); // Gets approved members only
                membershipFee = 0;

                for (ClaimsBean c : claims.getAllByStatus("Approved")) { // Gets approved claims only
                    claimsDouble += c.getAmount();
                }

                // The annual membership fee is calculated by dividing all approved claims from past year by approved members
                membershipFee = (claimsDouble / usersInt); 
                double membershipFeeTotal = Double.parseDouble(df.format(membershipFee));

                // Displays the total turnover for the past year, takes claims away from payments
                double turnover = Double.parseDouble(df.format(payments.annualTurnoverDouble() - claims.annualTurnoverDouble()));

                request.setAttribute("membershipFeeTotal", membershipFeeTotal);
                request.setAttribute("totalTurnover", turnover);
                break;

            case "suspendMembership": // Suspend a membership
                String suspendMembership = request.getParameter("suspendMembership");
                request.setAttribute("DASH_MSG", "Membership for " + suspendMembership + " suspended");
                System.out.println("uID: " + suspendMembership);

                if (suspendMembership != null) {
                    User tempUser = new User();
                    tempUser.setUsername(suspendMembership);
                    tempUser.setStatus(User.SUSPENDED);
                    members.updateStatus(tempUser);
                    users.updateStatus(tempUser);
                }

                break;

            case "annualFee": // Adds annual fee of £10 to 'Approved' members registered in past year
                request.setAttribute("DASH_MSG", "Annual fee of £10 charged to eligible members");
                for (User u : members.getAllByStatus("Approved")) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.YEAR, -1);
                    if (cal.getTimeInMillis() - u.getDor().getTime() >= 0) {
                        u.setBalance(u.getBalance() + 10);
                    }
                    members.updateBalance(u);
                }
                break;

            case "membershipFee": // Charges the membership fee to 'Approved' members
                claimsDouble = 0;
                usersInt = members.getAllByStatus("Approved").size();
                membershipFee = 0;

                // Math to work out the actual fee (approved claims / approved users)
                for (ClaimsBean c : claims.getAllByStatus("Approved")) {
                    claimsDouble += c.getAmount();
                }
                membershipFee = (claimsDouble / usersInt);
                double amount = Double.parseDouble(df.format(membershipFee));

                for (User u : members.getAllByStatus("Approved")) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.YEAR, -1);
                    if (cal.getTimeInMillis() - u.getDor().getTime() >= 0) {
                        u.setBalance(u.getBalance() + amount);
                        members.updateBalance(u);
                    }
                }

                request.setAttribute("DASH_MSG", "Membership fee of £" + amount + " charged to " + usersInt + " eligible members, sum of considered claims £" + claimsDouble);
                break;
        }
        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
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
        //String date = dateOfRegistration.toString();
        return port.validateClaim(dateOfRegistration, numberOfClaims, userStatus);
    }

}
