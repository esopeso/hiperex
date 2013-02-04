package fi.muni.redhat.hiperex.query.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

import nl.flotsam.xeger.Xeger;

import org.apache.log4j.Logger;

import fi.muni.redhat.hiperex.jdbc.JDBCConnectionProvider;
import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class UpdateQueryJDBC extends HibernateQuery implements Repeatable {

	private static Logger log = Logger.getLogger(SimpleSelectQueryJDBC.class);
	private JDBCConnectionProvider connectionProvider = null;
	private IdProvider idProvider;
	private Xeger generator = new Xeger("[a-z]{10}");

	public UpdateQueryJDBC(JDBCConnectionProvider connectionProvider,
			SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.idProvider = new IdProvider(factoryProvider, Customer.class);
		this.connectionProvider = connectionProvider;
	}

	public HashMap<String, Long> execute() {
		HashMap<String, Long> lapList = new HashMap<String, Long>();
		StopWatch timer = new StopWatch();
		String query = "Update product SET name = ? WHERE product_id = ?";
		String name = generator.generate();
		int id = -1;
		
		try {
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			Connection connection = connectionProvider.getC3P0Connection();
			timer.lap("connection");
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, id);
			timer.lap("query");
			ps.executeUpdate();
			connection.close();
			timer.lap("execution");
			timer.stop();
			lapList = timer.getLapTimeList();
		} catch (Exception ex) {
			ex.printStackTrace();
			timer.stop();
		}
		if (lapList.get("overall") == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed time of updating Customer by JDBC query: " + lapList);
		return lapList;
	}

}
