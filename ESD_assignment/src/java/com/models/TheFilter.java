package com.models;

import javax.servlet.*;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TheFilter implements Filter {

    private FilterConfig fc;

    public void init(FilterConfig config) throws ServletException {
        this.fc = config;
    }

    public void passRequest(User tempUser, ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("Authorised. Please continue."); // Method for an authorised user
        chain.doFilter(req, resp);  // doFilter causes the next filter in the chain to be invoked
    }

    public void authUser(User tempUser, ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (tempUser == null) {     // Method for unauthorised users
            req.setAttribute("ERR_MSG", "You do not have the correct authorisation to access this page");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        } else {
            passRequest(tempUser, req, resp, chain);
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;      // Servlet container creates a HTTPServletRequest object and passes it as an argument to - doGet and doPost etc
        HttpServletResponse httpResp = (HttpServletResponse) resp;

        String path = httpReq.getServletPath();

        HttpSession session = httpReq.getSession(false); // Returns the current HTTP session associated with this request or creates a new session if set to true. Returns null if no current session.
        String status = null;
        User tempUser = new User();  // Set up temporary user and set their status
        tempUser.setStatus(status);
        if (session != null) {
           status = (String) session.getAttribute("user");
        }
        System.out.println("status: " + status);
        switch (path) {
            case "/admin-dashboard.jsp":                // Members and users should not be able to access this page
                if (!status.trim().equals("ADMIN")) {   // If their status is not equal to ADMIN, throw an error message and redirect to the errorJSP
                    req.setAttribute("ERR_MSG", "You are not an admin");
                    req.getRequestDispatcher("error.jsp").forward(req, resp); // Request dispatcher used to forward the request to the resource
                }
                else {
                    System.out.println("Continue");     // Let them through if they are admin
                    chain.doFilter(req, resp);
                }
                   break;
                
            case "/member-dashboard.jsp":               // Only members should be accessing this page
                if (status.trim().equals("ADMIN")) {    // If they are admin, error msg and redirect to errorJSP
                    req.setAttribute("ERR_MSG", "You are not a member");
                    req.getRequestDispatcher("error.jsp").forward(req, resp);
                }
                else {
                    System.out.println("Continue");
                    chain.doFilter(req, resp);
                }
                    break;
                
            default:
                passRequest(tempUser, req, resp, chain);
                break;
        }

    }

    public void destroy() {  //destroy filter

    }

}
