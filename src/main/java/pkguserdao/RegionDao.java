package pkguserdao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import utils.DBUtil;

public class RegionDao {

    public boolean save(String region, String purpose, String people, String devices, String vehicle, String traffic, String ventilation) {
        try {
            // SQL query to insert data into the region_data table
            String query = "INSERT INTO region_data (region, purpose, people_count, devices, vehicle, traffic_level, ventilation) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement(query)) {

                // Null checking and setting values to the query
                ps.setString(1, (region != null && !region.isEmpty()) ? region.trim() : "Unknown");
                ps.setString(2, (purpose != null && !purpose.isEmpty()) ? purpose.trim() : "None");
                ps.setInt(3, (people != null && !people.isEmpty()) ? Integer.parseInt(people.trim()) : 0); 
                ps.setString(4, (devices != null && !devices.isEmpty()) ? devices.trim() : "None");
                ps.setString(5, (vehicle != null && !vehicle.isEmpty()) ? vehicle.trim() : "None");
                ps.setString(6, (traffic != null && !traffic.isEmpty()) ? traffic.trim() : "Unknown");
                ps.setString(7, (ventilation != null && !ventilation.isEmpty()) ? ventilation.trim() : "Unknown");

                // Execute the insert query and check if data was inserted
                int result = ps.executeUpdate();
                
                if (result > 0) {
                    // Data saved successfully, print confirmation message
                    System.out.println("Data in region table saved successfully.");
                    return true;
                } else {
                    
                    System.out.println("Failed to save data in region table.");
                    return false;
                }
            } catch (SQLException e) {
                // Print the exception message if an error occurs
                System.out.println("Error saving region data: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            System.out.println("General error in RegionDao.save: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
