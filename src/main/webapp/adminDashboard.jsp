<%@ page import="com.student.dao.StudentDao" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session == null || session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/adminLogin.jsp?type=error&message=Please+login");
        return;
    }
    StudentDao dao = new StudentDao();
    int total = 0;
    try {
        total = dao.getTotalStudents();
    } catch (Exception e) {
        total = 0;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Student Management</title>
    <link rel="stylesheet" href="css/admin.css">
</head>
<body>
<header class="admin-hero">
    <div class="hero-top">
        <div class="brand">Student Management</div>
        <div class="hero-actions">
            <a href="viewStudents.jsp" class="nav-link">View Students</a>
            <a href="viewStudents.jsp?q=" class="nav-link">Search</a>
            <a href="index.jsp" class="nav-link">Register</a>
            <a href="logout" class="nav-link btn-logout">Logout</a>
        </div>
    </div>
    <div class="hero-body">
        <h1>Admin Dashboard</h1>
    </div>
</header>

<main class="admin-main">
    <div class="cards-row">
        <div class="dashboard-card">
            <div class="card-icon">👥</div>
            <div class="card-body">
                <div class="card-title">Total Students</div>
                <div class="card-value"><%= total %></div>
                <div class="card-sub">Active & Registered</div>
            </div>
        </div>
        <div class="dashboard-card">
            <div class="card-icon">📚</div>
            <div class="card-body">
                <div class="card-title">Total Courses</div>
                <div class="card-value">10</div>
                <div class="card-sub">Available for Selection</div>
            </div>
        </div>
        <div class="dashboard-card">
            <div class="card-icon">⚡</div>
            <div class="card-body">
                <div class="card-title">System Status</div>
                <div class="card-value">Active</div>
                <div class="card-sub">Operational</div>
            </div>
        </div>
    </div>

    <div class="hero-actions-row">
        <a href="viewStudents.jsp" class="btn large primary">View Students</a>
        <a href="viewStudents.jsp?q=" class="btn large">Search Students</a>
        <a href="exportStudents" class="btn large secondary">Export Data</a>
    </div>

    <section style="margin-top:20px">
        <div style="background:#fff;padding:18px;border-radius:12px;color:#0b1220">
            <h3>Course Distribution</h3>
            <div style="display:flex;gap:12px;flex-wrap:wrap;margin-top:12px">
                <%-- fetch courses with counts --%>
                <%
                    com.student.dao.CourseDao courseDao = new com.student.dao.CourseDao();
                    java.util.List<com.student.model.Course> courseCounts = null;
                    try {
                        courseCounts = courseDao.getCoursesWithCounts();
                    } catch (Exception e) {
                        courseCounts = java.util.Collections.emptyList();
                    }
                %>
                <% for (com.student.model.Course c : courseCounts) { %>
                    <div style="min-width:180px;padding:12px;border-radius:10px;background:linear-gradient(180deg, rgba(124,58,237,0.06), rgba(6,182,212,0.03))">
                        <div style="font-weight:700"><%= c.getCourseName() %></div>
                        <div style="font-size:20px;font-weight:800;margin-top:8px"><%= c.getStudentCount() %></div>
                        <div class="small-muted">students</div>
                    </div>
                <% } %>
            </div>
        </div>
    </section>
</main>
</main>
</body>
</html>
