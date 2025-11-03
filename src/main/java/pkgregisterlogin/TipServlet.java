package pkgregisterlogin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pkguserdao.TipOnCarbonDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class TipServlet
 */
@WebServlet("/TipServlet")
public class TipServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			TipOnCarbonDao obj = new TipOnCarbonDao();


		    List<String> tips = new ArrayList<>();

	        //  Simulate DB results (for testing)
	        tips.add("Take shorter showers.");
	        tips.add("Unplug electronics when not in use.");
	        tips.add("Walk or bike for short trips.");
			request.setAttribute("tips", tips);
			RequestDispatcher rd = request.getRequestDispatcher("dashboard.jsp");
			rd.forward(request, response);
		} catch (Exception ex) {

		}
	}
}