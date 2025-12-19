package com.student.servlet;

import com.student.dao.StudentDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteStudent")
public class DeleteStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendRedirect(req.getContextPath() + "/viewStudents.jsp?type=error&message=Missing+id");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            StudentDao dao = new StudentDao();
            boolean ok = dao.deleteStudentById(id);
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/viewStudents.jsp?type=success&message=Student+deleted");
            } else {
                resp.sendRedirect(req.getContextPath() + "/viewStudents.jsp?type=error&message=Delete+failed");
            }
        } catch (NumberFormatException | SQLException ex) {
            resp.sendRedirect(req.getContextPath() + "/viewStudents.jsp?type=error&message=" + java.net.URLEncoder.encode(ex.getMessage(), "UTF-8"));
        }
    }
}
