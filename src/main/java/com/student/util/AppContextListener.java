package com.student.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize DBConnection with context params from web.xml
        DBConnection.init(sce.getServletContext());
        // Ensure default courses exist in DB
        try {
            com.student.dao.CourseDao courseDao = new com.student.dao.CourseDao();
            courseDao.ensureDefaultCourses();
        } catch (Exception e) {
            // log to console; avoid preventing startup
            System.err.println("Failed to ensure default courses: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // No-op
    }
}
