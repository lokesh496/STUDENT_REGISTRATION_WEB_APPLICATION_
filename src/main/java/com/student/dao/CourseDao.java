package com.student.dao;

import com.student.model.Course;
import com.student.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    public List<Course> getAllCourses() throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT id, course_name FROM courses ORDER BY course_name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Course c = new Course(rs.getInt("id"), rs.getString("course_name"));
                list.add(c);
            }
        }
        return list;
    }

    public List<Course> getCoursesWithCounts() throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT c.id, c.course_name, COUNT(s.id) AS cnt " +
                "FROM courses c LEFT JOIN students s ON s.course_id = c.id " +
                "GROUP BY c.id, c.course_name ORDER BY c.course_name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Course c = new Course(rs.getInt("id"), rs.getString("course_name"), rs.getInt("cnt"));
                list.add(c);
            }
        }
        return list;
    }

    public void ensureDefaultCourses() throws SQLException {
        String[] courses = new String[]{
                "Java",
                "Python",
                "C++",
                "Data Science",
                "Artificial Intelligence",
                "Machine Learning",
                "Web Development",
                "Cloud Computing",
                "Cyber Security",
                "DevOps"
        };
        String sql = "INSERT INTO courses (course_name, description) VALUES (?, NULL) ON CONFLICT (course_name) DO NOTHING";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String c : courses) {
                ps.setString(1, c);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
