<%@ page import="com.student.dao.StudentDao" %>
<%@ page import="com.student.model.Student" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session == null || session.getAttribute("admin") == null) {
        response.sendRedirect(request.getContextPath() + "/adminLogin.jsp?type=error&message=Please+login");
        return;
    }
    StudentDao dao = new StudentDao();
    List<Student> students = null;
    String q = request.getParameter("q");
    try {
        if (q != null && !q.trim().isEmpty()) {
            students = dao.searchStudents(q.trim());
        } else {
            students = dao.getAllStudents();
        }
    } catch (Exception e) {
        students = java.util.Collections.emptyList();
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Students - Student Management</title>
    <link rel="stylesheet" href="css/admin.css">
    <script>
        function confirmDelete(id) {
            if (confirm('Are you sure you want to delete this student?')) {
                window.location.href = 'deleteStudent?id=' + id;
            }
        }
    </script>
</head>
<body>
<nav class="topbar">
    <div class="brand">Student Management</div>
    <div class="nav-actions">
        <a href="adminDashboard.jsp" class="nav-link">Dashboard</a>
        <a href="logout" class="nav-link btn-logout">Logout</a>
    </div>
</nav>
<div class="container">
    <div style="display:flex;align-items:center;justify-content:space-between;gap:12px;flex-wrap:wrap">
        <h2>Student List</h2>
        <form method="get" action="viewStudents.jsp" style="margin:0">
            <input type="search" name="q" placeholder="Search students by name, email or course" value="<%= (q!=null?q:"") %>" style="padding:10px 12px;border-radius:10px;border:1px solid rgba(255,255,255,0.06);background:rgba(255,255,255,0.02);color:var(--text);width:320px;">
            <button type="submit" class="btn primary" style="padding:8px 12px;margin-left:8px">Search</button>
            <a href="viewStudents.jsp" class="btn secondary" style="margin-left:8px">Clear</a>
        </form>
    </div>
    <div class="message">
        <% String type = request.getParameter("type");
           String message = request.getParameter("message");
           if (message != null) {
        %>
        <div class="alert <%= ("success".equals(type) ? "alert-success" : "alert-error") %>"><%= message.replaceAll("\\+"," ") %></div>
        <% } %>
    </div>

    <div class="table-responsive">
        <table class="students-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Course</th>
                <th>Registered Date</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <% for (Student s : students) { %>
                <tr>
                    <td><%= s.getId() %></td>
                    <td><%= s.getName() %></td>
                    <td><%= s.getEmail() %></td>
                    <td><%= s.getCourse() %></td>
                    <td><%= s.getCreatedAt() %></td>
                    <td><button class="btn btn-danger" onclick="confirmDelete(<%= s.getId() %>)">Delete</button></td>
                </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
