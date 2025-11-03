package pkgregisterlogin;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.DBUtil;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportType = request.getParameter("reportType");
        String query = "";
        String reportTitle = "";

        if ("report1".equals(reportType)) {
            query = "SELECT * FROM registration";
            reportTitle = "User Registration Report";
        } else if ("report2".equals(reportType)) {
            query = "SELECT * FROM carbontips";
            reportTitle = "Carbon Tips Report";
        } else {
            request.setAttribute("error", "Invalid report type.");
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBUtil.getConnection();
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)
        ) {
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            // Column headers
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(meta.getColumnName(i));
            }

            // Data rows
            List<List<String>> rows = new ArrayList<>();
            while (rs.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                rows.add(row);
            }

            // Send to JSP
            request.setAttribute("columnNames", columnNames);
            request.setAttribute("rows", rows);
            request.setAttribute("reportTitle", reportTitle);
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }
    }
}