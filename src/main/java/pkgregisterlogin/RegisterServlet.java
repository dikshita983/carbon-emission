package pkgregisterlogin;

import pkguserdao.UserDao;
import pkguserdao.UserDaoImp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    @Override
    public void init() throws ServletException 
    {
        userDao = new UserDaoImp();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String age = request.getParameter("age");
        String password = request.getParameter("password");
        String confirmpassword = request.getParameter("confirmpwd");
        String healthstatus = request.getParameter("healthcondition");

        // Empty field check
        if (name == null || email == null || age == null || password == null || confirmpassword == null || healthstatus == null || 
            name.trim().isEmpty() || email.trim().isEmpty() || age.trim().isEmpty() || 
            password.trim().isEmpty() || confirmpassword.trim().isEmpty() || healthstatus.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Username validation
        if (!name.matches("^[a-zA-Z0-9]{4,20}$")) {
            request.setAttribute("error", "Username must be 4-20 characters long and contain only letters and numbers!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Email validation
        if (!email.matches("^[a-z][a-zA-Z0-9._-]*@[a-zA-Z0-9.-]+\\.(com|in|org)$")) {
            request.setAttribute("error", "Invalid email format! Email must start with a lowercase letter and end with .com, .in, or .org");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Age validation`
        try {
            int ageNum = Integer.parseInt(age);
            if (ageNum < 12 || ageNum > 100) {
                request.setAttribute("error", "Age must be between 12 and 100!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Age must be a valid number!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Password validation
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{6,15}$")) {
            request.setAttribute("error", "Password must be 6-15 characters and contain at least one uppercase letter, one lowercase letter, one number, and one special character!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Password match check
        if (!password.equals(confirmpassword)) {
            request.setAttribute("error", "Passwords do not match!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Health condition validation
        if (!healthstatus.matches("^[a-zA-Z\\s]+$")) {
            request.setAttribute("error", "Health condition must contain only letters and spaces!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Check if email already exists
        try {
            if (userDao.checkEmailExists(email)) {
                request.setAttribute("error", "Email is already registered!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An internal error occurred while checking email!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Save user and show success
        try {
            boolean isRegistered = userDao.registerUser(name, email, age, password, healthstatus);
            if (isRegistered) {
                HttpSession session = request.getSession();
                session.setAttribute("username", name);
                session.setAttribute("email", email);
                session.setAttribute("age", age);
                session.setAttribute("healthstatus", healthstatus);
                response.sendRedirect("login.jsp"); 
                return;
//                request.setAttribute("success", "You are registered successfully!");
//                request.getRequestDispatcher("register.jsp").forward(request, response);

            } else {
                request.setAttribute("error", "Registration failed! Please try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An internal error occurred. Please try again later.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }
}
