
package pkguserdao;

import java.sql.*;

import utils.DBUtil;

public class EmissionDao {

//    private Connection getConnection() throws SQLException {
//        
//        String url = "jdbc:mysql://localhost:3306/projectcrud";
//        String username = "root";
//        String password = "";
//        return DriverManager.getConnection(url, username, password);
//    }

    public double getDeviceEmission(String deviceName) {
        double emissionValue = 0.0;
        try (Connection conn = DBUtil.getConnection()){
        		//Connection conn = getConnection()) {
            String sql = "SELECT emission_value FROM devices WHERE name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, deviceName);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        emissionValue = rs.getDouble("emission_value");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  
        }
        return emissionValue;
    }

    public double getVehicleEmission(String vehicleType) {
        double emissionValue = 0.0;
        try (Connection conn = DBUtil.getConnection()){
        		//Connection conn = getConnection()) {
            String sql = "SELECT emission_value FROM vehicles WHERE type = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, vehicleType);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        emissionValue = rs.getDouble("emission_value");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emissionValue;
    }

    public double getTrafficFactor(String trafficLevel) {
        double factor = 0.0;
        try (Connection conn = DBUtil.getConnection()){
        		//Connection conn = getConnection()) {
            String sql = "SELECT factor FROM traffic_levels WHERE level = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, trafficLevel);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        factor = rs.getDouble("factor");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factor;
    }
}