package com.student.dao;

import com.student.model.Student;
import com.student.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    public boolean insertStudent(Student student) throws SQLException {
        // Insert into normalized students table. Map course name -> course_id
        String findCourse = "SELECT id FROM courses WHERE course_name = ? LIMIT 1";
        String insert = "INSERT INTO students (name, email, course_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psFind = conn.prepareStatement(findCourse)) {
                psFind.setString(1, student.getCourse());
                int courseId = -1;
                try (ResultSet rs = psFind.executeQuery()) {
                    if (rs.next()) {
                        courseId = rs.getInt("id");
                    }
                }

                // If course not found, treat as invalid selection (do not auto-create)
                if (courseId == -1) {
                    conn.rollback();
                    return false;
                }

                try (PreparedStatement psIns = conn.prepareStatement(insert)) {
                    psIns.setString(1, student.getName());
                    psIns.setString(2, student.getEmail());
                    psIns.setInt(3, courseId);
                    int rows = psIns.executeUpdate();
                    conn.commit();
                    return rows > 0;
                }
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM students WHERE email = ? LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        // join students with courses to return course name
        String sql = "SELECT s.id, s.name, s.email, c.course_name AS course, s.created_at " +
                "FROM students s JOIN courses c ON s.course_id = c.id ORDER BY s.id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course"),
                        rs.getTimestamp("created_at")
                );
                list.add(s);
            }
        }
        return list;
    }

    public List<Student> searchStudents(String q) throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT s.id, s.name, s.email, c.course_name AS course, s.created_at " +
                "FROM students s JOIN courses c ON s.course_id = c.id " +
                "WHERE s.name ILIKE ? OR s.email ILIKE ? OR c.course_name ILIKE ? " +
                "ORDER BY s.id DESC";
        String pattern = "%" + q + "%";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("course"),
                            rs.getTimestamp("created_at")
                    );
                    list.add(s);
                }
            }
        }
        return list;
    }

    public boolean deleteStudentById(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    public int getTotalStudents() throws SQLException {
        String sql = "SELECT COUNT(*) FROM students";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
