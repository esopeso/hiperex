package fi.muni.redhat.hiperex.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;

import fi.muni.redhat.hiperex.jdbc.JDBCConnection;
import fi.muni.redhat.hiperex.util.Repeatable;

public class MultipleJoinQueryJDBC implements Repeatable{

	JDBCConnection connectionProvider = new JDBCConnection();

	public long repeat() {
		String query = "SELECT name " +
							"FROM product NATURAL JOIN " +
								"(SELECT product_id " +
									"FROM item_list NATURAL JOIN " +
										"(SELECT order_id " +
											"FROM customer_order " +
											"WHERE customer_id= ?) " +
										"AS A) " +
								"AS B";
		long time = 0;
		StopWatch timer = new StopWatch();
		ResultSet rs;
		
		try {
			timer.start();
			Connection connection = connectionProvider.getJDBCConection();
			java.sql.PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, 69);
			rs = ps.executeQuery();
			List<String> resultList = new LinkedList<String>();
			while (rs.next()) {
				resultList.add(rs.getString("name"));
			}
			connection.close();
			timer.stop();
			time = timer.getTime();
			//System.out.println("Elapsed time of JDBC query: "+time);
			return time;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	
	
}
