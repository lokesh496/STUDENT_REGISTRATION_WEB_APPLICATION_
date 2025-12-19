<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Student Registration - Student Management</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="bg-gradient">
    <div class="card center-card">
        <h2>Register Student</h2>

        <%@ page import="com.student.dao.CourseDao" %>
        <%@ page import="com.student.model.Course" %>
        <%
            CourseDao courseDao = new CourseDao();
            java.util.List<Course> courseList = null;
            try {
                courseList = courseDao.getAllCourses();
            } catch (Exception e) {
                courseList = java.util.Collections.emptyList();
            }
        %>

        <form action="register" method="post" class="form">
            <label for="name">Name</label>
            <input id="name" name="name" type="text" placeholder="Full name"
                   pattern="[A-Za-z ]+" title="Only letters and spaces allowed" required>

            <label for="email">Email</label>
            <input id="email" name="email" type="email" placeholder="name@example.com"
                   pattern="^[A-Za-z][A-Za-z0-9._%+-]*@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$"
                   title="Must be a valid email and not start with a number" required>

            <label for="course">Course</label>
            <div class="custom-select" id="courseSelect">
                <input type="hidden" name="course" id="course" required>
                <div class="select-trigger" tabindex="0">-- Select Course --</div>
                <div class="options" style="display:none;">
                    <% for (Course c : courseList) { %>
                        <div class="option" data-value="<%= c.getCourseName() %>"><%= c.getCourseName() %></div>
                    <% } %>
                </div>
            </div>

            <div class="buttons">
                <button type="submit" class="btn primary">Register Student</button>
                <a href="adminLogin.jsp" class="btn secondary">Admin Login</a>
            </div>
        </form>

        <div class="message">
            <% String type = request.getParameter("type");
               String message = request.getParameter("message");
               if (message != null) {
            %>
            <div class="alert <%= ("success".equals(type) ? "alert-success" : "alert-error") %>"><%= message.replaceAll("\\+"," ") %></div>
            <% } %>
        </div>
    </div>
</div>
<script>
    (function(){
        const cs = document.getElementById('courseSelect');
        if(!cs) return;
        const trigger = cs.querySelector('.select-trigger');
        const options = cs.querySelector('.options');
        const hidden = document.getElementById('course');

        trigger.addEventListener('click', ()=>{
            const open = options.style.display === 'block';
            options.style.display = open ? 'none' : 'block';
        });

        options.addEventListener('click', (e)=>{
            const opt = e.target.closest('.option');
            if(!opt) return;
            const val = opt.getAttribute('data-value');
            hidden.value = val;
            trigger.textContent = val;
            options.style.display = 'none';
        });

        document.addEventListener('click', (e)=>{
            if(!cs.contains(e.target)) options.style.display = 'none';
        });
    })();
</script>
</body>
</html>
