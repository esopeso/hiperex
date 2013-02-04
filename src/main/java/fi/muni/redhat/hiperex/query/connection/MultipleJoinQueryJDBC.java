package fi.muni.redhat.hiperex.query.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import fi.muni.redhat.hiperex.timer.StopWatch;
import org.apache.log4j.Logger;

import fi.muni.redhat.hiperex.jdbc.JDBCConnectionProvider;
import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class MultipleJoinQueryJDBC implements Repeatable {

	private static Logger log = Logger.getLogger(MultipleJoinQueryJDBC.class);
	private JDBCConnectionProvider connectionProvider = null;
	private IdProvider idProvider;
	StopWatch timer = new StopWatch();

	/**
	 * Constructor for dynamic query generation (random id substitution)
	 * 
	 * @param factoryProvider
	 *            SessionFactoryProvider used for idProvider
	 */
	public MultipleJoinQueryJDBC(JDBCConnectionProvider connectionProvider, SessionFactoryProvider factoryProvider) {
		idProvider = new IdProvider(factoryProvider, Customer.class);
		this.connectionProvider = connectionProvider;
	}

	/* @foff */
	//SELECT name FROM product NATURAL JOIN (SELECT product_id FROM item_list NATURAL JOIN SELECT order_id FROM customer_order WHERE customer_id= ?)AS A)AS B;  
	//SELECT DISTINCT name FROM product INNER JOIN (SELECT product_id FROM item_list INNER JOIN (SELECT order_id FROM customer_order WHERE customer_id= 55)AS A ON item_list.order_id=A.order_id)AS B ON product.product_id=B.product_id;
	public HashMap<String, Long> execute() {
		String query = "SELECT DISTINCT name " +
							"FROM product INNER JOIN " +
								"(SELECT product_id " +
									"FROM item_list INNER JOIN " +
										"(SELECT order_id " +
											"FROM customer_order " +
											"WHERE customer_id= ?) " +
										"AS A ON item_list.order_id=A.order_id) " +
								"AS B ON product.product_id=B.product_id";
		/*@fon*/
		HashMap<String, Long> lapList = new HashMap<String, Long>();
		ResultSet rs = null;
		int id = -1;

		try {
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			Connection connection = connectionProvider.getBoneConnection();
			timer.lap("connection");
			java.sql.PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, id);
			timer.lap("query");
			rs = ps.executeQuery();
			List<String> resultList = new LinkedList<String>();
			while (rs.next()) {
				resultList.add(rs.getString("name"));
			}
			connection.close();
			timer.lap("execution");
			timer.stop();
			lapList = timer.getLapTimeList();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			timer.reset();
		}
		if (lapList.get("overall") == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed time of JDBC query: " + lapList);
		return lapList;
	}

}
