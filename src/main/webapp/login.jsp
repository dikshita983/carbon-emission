<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
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
        .error {
            color: red;
            margin-top: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="left-panel">
            <h2>Login</h2>
            <p>Welcome back! Please login to continue.</p>
        </div>
        <div class="right-panel">
            <form action="LoginServlet" method="post" >
                <label>Email</label>
                <input type="email" name="email" placeholder="Enter Email" value="<%= request.getAttribute("registeredEmail") != null ? request.getAttribute("registeredEmail") : "" %>" required >
                
                <label>Password</label>
                <input type="password" name="password" placeholder="Enter Password" required autocomplete="new-password"> 
                
                <button type="submit">Login</button>
                
                <div class="register-link">
                    <p>Don't have an account? <a href="register.jsp">Sign up here</a></p>
                </div>
                
                <!-- Error messages -->
                <% if (request.getParameter("error") != null) { %>
                    <div class="error">
                        <% if (request.getParameter("error").equals("invalid_credentials")) { %>
                            Invalid email or password
                        <% } else if (request.getParameter("error").equals("empty_fields")) { %>
                            Please fill in all fields
                        <% } %>
                    </div>
                <% } %>
            </form>
        </div>
    </div>
</body>
</html>