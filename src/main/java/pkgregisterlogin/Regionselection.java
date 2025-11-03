package pkgregisterlogin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import pkguserdao.EmissionDao;
import pkguserdao.RegionDao;
import pkguserdao.UserDao;
import pkguserdao.UserDaoImp;
import utils.SuggestionUtil;

@WebServlet("/RegionSelection")
public class Regionselection extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String region = request.getParameter("region");
        String purpose = request.getParameter("purpose");
        String people = request.getParameter("people");
        String[] devicesArray = request.getParameterValues("devices");
        String devices = (devicesArray != null) ? String.join(",", devicesArray) : "None";
        String vehicle = request.getParameter("vehicle");
        String traffic = request.getParameter("traffic");
        String ventilation = request.getParameter("ventilation");

        // Validation
        if (region == null || purpose == null || people == null || traffic == null || ventilation == null ) {
            response.getWriter().println("Missing required parameters.");
            return;
        }

        int peopleCount = 0;
        try {
            peopleCount = Integer.parseInt(people);
        } catch (NumberFormatException e) 
        {
            peopleCount = 0;
        }


        // Save region info
        RegionDao dao = new RegionDao();
        boolean isSaved = dao.save(region, purpose, people, devices, vehicle, traffic, ventilation);

        UserDao userDaoImp = new UserDaoImp(); 
        HttpSession session = request.getSession(false);  // Get session without creating new
        if (session == null || session.getAttribute("email") == null) {
            response.getWriter().println("User session not found. Please login again.");
            return;
        }
        String email = (String) session.getAttribute("email");


        
        String healthCondition = userDaoImp.getHealthByEmail(email);  
        int age = userDaoImp.getAgeByEmail(email);
        if (isSaved) {
            EmissionDao emissionDao = new EmissionDao();
            double emissionPerHour = calculateEmission(devices, vehicle, traffic, peopleCount, emissionDao);
            double emissionPerMinute = emissionPerHour / 60.0;

            // Generate dynamic suggestions
            String suggestionMessage = SuggestionUtil.getSuggestions(region, emissionPerHour, healthCondition, age);


            // Set attributes for the dashboard
            request.setAttribute("region", region);
            request.setAttribute("purpose", purpose);
            request.setAttribute("people", peopleCount);
            request.setAttribute("devices", devices);
            request.setAttribute("vehicle", vehicle);
            request.setAttribute("traffic", traffic);
            request.setAttribute("ventilation", ventilation);
            request.setAttribute("emission", emissionPerHour);
            request.setAttribute("emissionPerMinute", emissionPerMinute);
            request.setAttribute("suggestions", suggestionMessage);

            // Forward to dashboard.jsp
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        } else {
            response.getWriter().println("Failed to save data.");
        }
    }

    private double calculateEmission(String devices, String vehicle, String traffic, int people, EmissionDao emissionDao) {
        double totalEmission = 0.0;
        String[] deviceList = devices.split(",");
        for (String device : deviceList) {
            totalEmission += emissionDao.getDeviceEmission(device.trim());
        }
        totalEmission += emissionDao.getVehicleEmission(vehicle);
        totalEmission += emissionDao.getTrafficFactor(traffic);
        totalEmission += people * 0.1;  // Basic assumption: 0.1 kg CO2/hour per person
        return totalEmission;
    }
}
