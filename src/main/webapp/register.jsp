<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background: linear-gradient(to right, #002f7e, #0052cc);
        }
        .container {
            display: flex;
            width: 700px;
            background: #fff;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        }
        .left-panel {
            background: #003366;
            color: white;
            padding: 40px;
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }
        .right-panel {
            flex: 2;
            padding: 40px;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin: 10px 0 5px;
            font-weight: bold;
        }
        input {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            margin-top: 20px;
            padding: 10px;
            background: #0052cc;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background: #003f99;
        }
        .register-link {
            text-align: center;
            margin-top: 20px;
        }
        .register-link a {
            color: #0052cc;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="left-panel">
            <h2>Sign Up</h2>
            <p>Register to access our services.</p>
        </div>
        <div class="right-panel">
            <form action="RegisterServlet" method="post">

                <%-- SUCCESS MESSAGE --%>
              <%
    String successMsg = (String) request.getAttribute("success");
    if (successMsg != null) {
%>
    <p style="color: green;"><%= successMsg %></p>
    <script>
        setTimeout(function() {
            window.location.href = 'login.jsp';
        }, 1000); // Redirect after 1 seconds
    </script>
<%
    }
%>


                <%-- ERROR MESSAGE --%>
                <% if(request.getAttribute("error") != null) { %>
                    <div class="error-message" style="color: red; margin: 10px 0; padding: 10px; background-color: #ffe6e6; border-radius: 5px;">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>

                <label>Username</label>
                <input type="text" name="name" placeholder="Enter Username" 
                    value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>" required>

                <label>Email</label>
                <input type="email" name="email" placeholder="Enter Email" 
                    value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" required>

                <label>Age</label>
                <input type="text" name="age" placeholder="Enter age" 
                    value="<%= request.getParameter("age") != null ? request.getParameter("age") : "" %>" required>

                <label>Password</label>
                <input type="password" name="password" placeholder="Enter Password" 
                    value="<%= request.getParameter("password") != null ? request.getParameter("password") : "" %>" required>

                <label>Confirm Password</label>
                <input type="password" name="confirmpwd" placeholder="Confirm Password" 
                    value="<%= request.getParameter("confirmpwd") != null ? request.getParameter("confirmpwd") : "" %>" required>

                <label>Health Condition</label>
                <input type="text" name="healthcondition" placeholder="Enter Health Condition" 
                    value="<%= request.getParameter("healthcondition") != null ? request.getParameter("healthcondition") : "" %>" required>

                <button type="submit">Register</button>
                <div class="register-link">
                    <p>Already Registered? <a href="login.jsp">Sign in here</a></p>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
