package fi.muni.redhat.hiperex.query.delete;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import fi.muni.redhat.hiperex.jdbc.JDBCConnectionProvider;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class DeleteJDBC implements Repeatable{

	private static Logger log = Logger.getLogger(DeleteJDBC.class);
	private JDBCConnectionProvider connectionProvider = new JDBCConnectionProvider();
	private StopWatch timer = new StopWatch();
	private IdProvider idProvider;
	int i = 1;
	
	public DeleteJDBC(SessionFactoryProvider factoryProvider) {
		this.idProvider = new IdProvider(factoryProvider, "SELECT productId FROM Product WHERE productId>500");
	}
	
	public long repeat() {
		long time = 0;
		
		try {
		timer.start();
		Connection connection = connectionProvider.getJDBCConection();
		PreparedStatement ps = connection.prepareStatement("DELETE FROM product WHERE product_id = ?");
		int id = idProvider.getRandomId();
		ps.setInt(1, id);
		ps.executeUpdate();
		connection.close();
		timer.stop();
		idProvider.removeId(id);
		time = timer.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			timer.reset();
		}
		timer.reset();
		log.debug("Elapsed execution time of JDBC delete query: "+time);
		return time;
	}
	
	public IdProvider getIdProvider() {
		return this.idProvider;
	}

}
