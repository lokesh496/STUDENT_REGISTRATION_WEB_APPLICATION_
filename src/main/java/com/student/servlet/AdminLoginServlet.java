package com.student.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {

    private static final String ADMIN_USER = "iamneo";
    private static final String ADMIN_PASS = "iamneo123";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (ADMIN_USER.equals(username) && ADMIN_PASS.equals(password)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("admin", username);
            resp.sendRedirect(req.getContextPath() + "/adminDashboard.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/adminLogin.jsp?type=error&message=Invalid+username+or+password");
        }
    }
}
