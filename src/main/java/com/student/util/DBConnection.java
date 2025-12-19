package com.student.util;

import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility to obtain DB connections.
 *
 * Behavior:
 * - First checks environment variables `DB_URL`, `DB_USER`, `DB_PASSWORD`.
 * - If not set, falls back to values initialized from Servlet `context-param` (via AppContextListener).
 */
public class DBConnection {
    private static volatile String urlFromContext;
    private static volatile String userFromContext;
    private static volatile String passFromContext;

    // Called by AppContextListener to initialize context parameters
    public static void init(ServletContext ctx) {
        if (ctx == null) return;
        String u = ctx.getInitParameter("DB_URL");
        String usr = ctx.getInitParameter("DB_USER");
        String pwd = ctx.getInitParameter("DB_PASSWORD");
        if (u != null && !u.isBlank()) urlFromContext = u.trim();
        if (usr != null && !usr.isBlank()) userFromContext = usr.trim();
        if (pwd != null && !pwd.isBlank()) passFromContext = pwd.trim();
    }

    public static Connection getConnection() throws SQLException {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASSWORD");

        // fallback to context params if env vars not provided
        if ((url == null || url.isBlank()) && urlFromContext != null) url = urlFromContext;
        if ((user == null || user.isBlank()) && userFromContext != null) user = userFromContext;
        if ((pass == null || pass.isBlank()) && passFromContext != null) pass = passFromContext;

        if (url == null || user == null) {
            throw new SQLException("Database credentials not set. Set DB_URL, DB_USER, DB_PASSWORD environment variables or provide context params in web.xml.");
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Postgres JDBC Driver not found.", e);
        }

        return DriverManager.getConnection(url, user, pass);
    }
}
