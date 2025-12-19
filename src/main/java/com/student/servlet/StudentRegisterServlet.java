package com.student.servlet;

import com.student.dao.StudentDao;
import com.student.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class StudentRegisterServlet extends HttpServlet {

    private static final String NAME_REGEX = "^[A-Za-z ]+$";
    private static final String EMAIL_REGEX = "^[A-Za-z][A-Za-z0-9._%+-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String course = req.getParameter("course");

        if (name == null || email == null || course == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?type=error&message=Missing+fields");
            return;
        }

        name = name.trim();
        email = email.trim();

        if (!name.matches(NAME_REGEX)) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?type=error&message=Invalid+name");
            return;
        }

        if (!email.matches(EMAIL_REGEX)) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?type=error&message=Invalid+email");
            return;
        }

        StudentDao dao = new StudentDao();
        try {
            if (dao.isEmailExists(email)) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp?type=error&message=Email+already+registered");
                return;
            }

            Student s = new Student(name, email, course);
            boolean ok = dao.insertStudent(s);
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp?type=success&message=Registration+successful");
            } else {
                // likely invalid course selection
                resp.sendRedirect(req.getContextPath() + "/index.jsp?type=error&message=Invalid+course+selected");
            }
        } catch (SQLException ex) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?type=error&message=" + java.net.URLEncoder.encode(ex.getMessage(), "UTF-8"));
        }
    }
}
