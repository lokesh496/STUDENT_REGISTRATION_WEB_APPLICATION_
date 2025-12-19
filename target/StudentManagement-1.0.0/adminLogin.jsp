<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Login - Student Management</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/admin.css">
</head>
<body>
<div class="bg-gradient">
    <div class="card center-card" style="max-width:420px">
        <div style="text-align:center;margin-bottom:12px">
            <div style="width:64px;height:64px;margin:0 auto;border-radius:12px;background:linear-gradient(90deg,#06b6d4,#7c3aed);display:flex;align-items:center;justify-content:center;color:#fff;font-weight:700;font-size:20px">SM</div>
            <h2 style="margin-top:10px">Admin Login</h2>
            <div style="color:var(--muted);font-size:14px">Sign in to manage students</div>
        </div>
        <form action="adminLogin" method="post" class="form">
            <label for="username">Username</label>
            <input id="username" name="username" type="text" placeholder="Username" required>

            <label for="password">Password</label>
            <input id="password" name="password" type="password" placeholder="Password" required>

            <div class="buttons">
                <button type="submit" class="btn primary">Login</button>
                <a href="index.jsp" class="btn secondary">Back</a>
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
</body>
</html>
