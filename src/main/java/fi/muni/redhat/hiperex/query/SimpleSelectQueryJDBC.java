package fi.muni.redhat.hiperex.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import fi.muni.redhat.hiperex.exception.NotUniqueIdException;
import fi.muni.redhat.hiperex.jdbc.JDBCConnection;
import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.util.Repeatable;

public class SimpleSelectQueryJDBC implements Repeatable{
	
	private static Logger log = Logger.getLogger(SimpleSelectQueryJDBC.class);
	private JDBCConnection connectionProvider = new JDBCConnection();

	public long repeat() {
		long time = 0;
		StopWatch timer = new StopWatch();
		timer.start();
		int i = 0;
		Customer customer = new Customer();
		ResultSet rs;
		String query = "SELECT * FROM customer WHERE customer_id = ?";

		try {
		Connection connection = connectionProvider.getJDBCConection();
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, 50);
		rs = ps.executeQuery();

		while (rs.next()) {
			customer.setCustomerId(Integer.parseInt(rs.getString("customer_id")));
			customer.setFirstName(rs.getString("first_name"));
			customer.setLastName(rs.getString("last_name"));
			customer.setBirthDate(rs.getDate("birth_date"));
			customer.setPhoneNumber(rs.getString("phone_number"));
			customer.setAddress(rs.getString("address"));
			i++;
			if (i > 1)
				throw new NotUniqueIdException("Duplicated ID detected");
		}
		connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		timer.stop();
		time = timer.getTime();
		log.debug("Elapsed time of mapping Customer(50) by JDBC: "+time);
		return time;
	}
}
