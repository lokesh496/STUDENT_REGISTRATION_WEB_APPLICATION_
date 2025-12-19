package com.student.servlet;

import com.student.dao.StudentDao;
import com.student.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/exportStudents")
public class ExportCsvServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=students.csv");
        try (PrintWriter out = resp.getWriter()) {
            out.println("id,name,email,course,created_at");
            StudentDao dao = new StudentDao();
            List<Student> list = dao.getAllStudents();
            for (Student s : list) {
                String line = String.format("%d,\"%s\",%s,%s,%s", s.getId(), s.getName().replace("\"", "''"), s.getEmail(), s.getCourse(), s.getCreatedAt());
                out.println(line);
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
