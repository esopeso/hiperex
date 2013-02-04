package fi.muni.redhat.hiperex.query.old;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import nl.flotsam.xeger.Xeger;

import org.apache.log4j.Logger;

import fi.muni.redhat.hiperex.jdbc.JDBCConnectionProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.old.Repeatable;

public class InsertJDBC implements Repeatable {

	private static Logger log = Logger.getLogger(InsertJDBC.class);
	private JDBCConnectionProvider connectionProvider = null;
	private Xeger generator = new Xeger("[a-z]{10}");
	private StopWatch timer = new StopWatch();

	public InsertJDBC(JDBCConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	public long execute() {
		Connection connection = null;
		PreparedStatement ps = null;
		long time = 0;
		String query = "INSERT INTO product(name) VALUES(?)";
		String name = generator.generate();
		
		try {
			timer.reset();
			timer.start();
			connection = connectionProvider.getBoneConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(query);
			ps.setString(1, name);
			ps.executeUpdate();
			connection.commit();
			connection.close();
			timer.stop();
			time = timer.getTime();
		} catch (SQLException e) {
			try {
				if (connection != null) connection.rollback();
			} catch (SQLException se) {
				log.info("Couldn't roll back transaction");
			}
			try {
				if (connection != null) connection.close();
			} catch (SQLException se) {
				log.info("Couldn't close connection");
			}
			e.printStackTrace();
			timer.reset();
		}
		if (time == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed time of executing SQL insert: " + time);
		return time;
	}

}
