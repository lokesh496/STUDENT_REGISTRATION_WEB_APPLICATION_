package com.student.servlet;

import com.student.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/dbtest")
public class DbTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            try (Connection conn = DBConnection.getConnection()) {
                if (conn == null || conn.isClosed()) {
                    out.println("DB: not connected");
                    return;
                }

                String courseCountSql = "SELECT COUNT(*) FROM courses";
                String studentCountSql = "SELECT COUNT(*) FROM students";

                try (PreparedStatement ps1 = conn.prepareStatement(courseCountSql);
                     ResultSet rs1 = ps1.executeQuery()) {
                    if (rs1.next()) out.println("courses=" + rs1.getInt(1));
                } catch (Exception e) {
                    out.println("courses=error: " + e.getMessage());
                }

                try (PreparedStatement ps2 = conn.prepareStatement(studentCountSql);
                     ResultSet rs2 = ps2.executeQuery()) {
                    if (rs2.next()) out.println("students=" + rs2.getInt(1));
                } catch (Exception e) {
                    out.println("students=error: " + e.getMessage());
                }

                out.println("DB: ok");
            }
        } catch (Exception ex) {
            resp.getWriter().println("error: " + ex.getMessage());
        }
    }
}
