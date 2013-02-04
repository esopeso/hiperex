package fi.muni.redhat.hiperex.query.old;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import fi.muni.redhat.hiperex.jdbc.JDBCConnectionProvider;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.old.Repeatable;

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

	public long execute() {
		Connection connection = null;
		long time = 0;
		String queryString = "DELETE FROM data.product WHERE product_id = ?";
		int id = -1;

		try {
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			connection = connectionProvider.getC3P0Connection();
			connection.setAutoCommit(false);
			
			PreparedStatement ps = connection.prepareStatement(queryString);
			ps.setInt(1, id);
			
			ps.executeUpdate();
			connection.commit();
			connection.close();
			timer.stop();
			idProvider.removeId(id);
			time = timer.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			timer.reset();
		}
		if (time == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed execution time of JDBC delete query: " + time);
		return time;
	}
	
	//public IdProvider getIdProvider() {
	//	return this.idProvider;
	//}

}
