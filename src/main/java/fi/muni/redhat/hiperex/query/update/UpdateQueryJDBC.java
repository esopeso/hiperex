package fi.muni.redhat.hiperex.query.update;

import java.sql.Connection;
import java.sql.PreparedStatement;

import nl.flotsam.xeger.Xeger;

import org.apache.log4j.Logger;

import fi.muni.redhat.hiperex.jdbc.JDBCConnectionProvider;
import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.query.select.SimpleSelectQueryJDBC;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class UpdateQueryJDBC extends HibernateQuery implements Repeatable {
	
	private static Logger log = Logger.getLogger(SimpleSelectQueryJDBC.class);
	private JDBCConnectionProvider connectionProvider = new JDBCConnectionProvider();
	private IdProvider idProvider;
	private Xeger generator = new Xeger("[a-z]{10}");
	
	public UpdateQueryJDBC(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.idProvider = new IdProvider(factoryProvider, Customer.class);
	}

	public long repeat() {
		long time = 0;
		StopWatch timer = new StopWatch();
		String query = "Update product SET name = ? WHERE product_id = ?";
		String name = generator.generate();
		try {
		timer.start();
		Connection connection = connectionProvider.getJDBCConection();
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, name);
		ps.setInt(2, idProvider.getRandomId());
		ps.executeUpdate();

		connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			timer.stop();
		}
		timer.stop();
		time = timer.getTime();
		log.debug("Elapsed time of updating Customer by JDBC query: "+time);
		return time;
	}

}
