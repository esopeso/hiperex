package fi.muni.redhat.hiperex.query.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fi.muni.redhat.hiperex.jdbc.JDBCConnectionProvider;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class DeleteJDBC implements Repeatable {

	private static Logger log = Logger.getLogger(DeleteJDBC.class);
	private JDBCConnectionProvider connectionProvider = null;
	private StopWatch timer = new StopWatch();
	private IdProvider idProvider;

	public DeleteJDBC(JDBCConnectionProvider connectionProvider,
			SessionFactoryProvider factoryProvider) {
		this.idProvider = new IdProvider(factoryProvider,
				"SELECT productId FROM Product WHERE productId>500");
		this.connectionProvider = connectionProvider;
	}

	public HashMap<String, Long> execute() {
		Connection connection = null;
		HashMap<String, Long> lapList = new HashMap<String, Long>();
		String queryString = "DELETE FROM data.product WHERE product_id = ?";
		int id = -1;

		try {
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			connection = connectionProvider.getC3P0Connection();
			timer.lap("connection");
			connection.setAutoCommit(false);
			
			PreparedStatement ps = connection.prepareStatement(queryString);
			ps.setInt(1, id);
			timer.lap("query");
			
			ps.executeUpdate();
			connection.commit();
			connection.close();
			timer.lap("execution");
			timer.stop();
			idProvider.removeId(id);
			lapList = timer.getLapTimeList();
		} catch (Exception e) {
			e.printStackTrace();
			timer.reset();
		}
		if (lapList.get("overall") == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed execution time of JDBC delete query: " + lapList);
		return lapList;
	}

}
