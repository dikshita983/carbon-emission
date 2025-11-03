package pkgregisterlogin;
import pkguserdao.Logindao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logindao logindao;
	public void init() throws ServletException {
        logindao = new Logindao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.sendRedirect("login.jsp");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (email == null || password == null || 
            email.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect("login.jsp?error=empty_fields");
            return;
        }

        try {
            if (logindao.validate(email, password)) {
                // Get user details after successful validation
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                
                // Get and set additional user details
                String[] userDetails = logindao.getUserDetails(email);
                if (userDetails != null) {
                    session.setAttribute("username", userDetails[0]);
                    session.setAttribute("age", userDetails[1]);
                    session.setAttribute("healthstatus", userDetails[2]);
                }
                
                response.sendRedirect("dashboard.jsp");
            } else {
                response.sendRedirect("login.jsp?error=invalid_credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=internal_error");
        }
    }
	        
	    }

	    
		

	
