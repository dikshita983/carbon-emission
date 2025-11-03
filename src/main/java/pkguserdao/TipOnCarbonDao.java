package pkguserdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.DBUtil;

public class TipOnCarbonDao {

	    public List<String> fetchTips() {
	        List<String> tips = new ArrayList<>();
	        try {
	        	Connection conn = DBUtil.getConnection();
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT tip_text FROM CarbonTips");

	            while (rs.next()) {
	                tips.add(rs.getString("tip_text"));
	            }

	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        System.out.println(tips);
	        
	        return tips;
	    }
	

}
