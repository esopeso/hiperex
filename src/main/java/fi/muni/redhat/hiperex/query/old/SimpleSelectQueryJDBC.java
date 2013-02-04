package fi.muni.redhat.hiperex.query.old;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import fi.muni.redhat.hiperex.timer.StopWatch;
import org.apache.log4j.Logger;

import fi.muni.redhat.hiperex.exception.NotUniqueIdException;
import fi.muni.redhat.hiperex.jdbc.JDBCConnectionProvider;
import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.old.Repeatable;

public class SimpleSelectQueryJDBC implements Repeatable {

	private static Logger log = Logger.getLogger(SimpleSelectQueryJDBC.class);
	private JDBCConnectionProvider connectionProvider = null;
	private StopWatch timer = new StopWatch();
	private IdProvider idProvider;

	public SimpleSelectQueryJDBC(JDBCConnectionProvider connectionProvider,
			SessionFactoryProvider factoryProvider) {
		this.idProvider = new IdProvider(factoryProvider, Customer.class);
		this.connectionProvider = connectionProvider;
	}

	public long execute() {
		long time = 0;
		int i = 0;
		Customer customer = new Customer();
		ResultSet rs;
		int id = -1;
		String query = "SELECT * FROM customer WHERE customer_id = ?";	

		try {
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			Connection connection = connectionProvider.getC3P0Connection();

			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				customer.setCustomerId(Integer.parseInt(rs
						.getString("customer_id")));
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
			timer.stop();
			time = timer.getTime();
		} catch (Exception ex) {
			ex.printStackTrace();
			timer.reset();
		}
		if (time == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed time of mapping Customer by JDBC: " + time);
		return time;
	}
}
