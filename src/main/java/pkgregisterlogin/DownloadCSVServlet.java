package pkgregisterlogin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/downloadCSV")
public class DownloadCSVServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"emission_report.csv\"");

        PrintWriter out = response.getWriter();

        // CSV Header
        out.println("Region,Purpose,People,Devices,Vehicle,Traffic,Ventilation,Emission (kg CO2),Emission per Minute (kg CO2)");

        // CSV Data
        out.printf("%s,%s,%s,%s,%s,%s,%s,%.2f,%.4f\n",
                request.getParameter("region"),
                request.getParameter("purpose"),
                request.getParameter("people"),
                request.getParameter("devices"),
                request.getParameter("vehicle"),
                request.getParameter("traffic"),
                request.getParameter("ventilation"),
                Double.parseDouble(request.getParameter("emission")),
                Double.parseDouble(request.getParameter("emissionPerMinute"))
        );
    }
}