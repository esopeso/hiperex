package fi.muni.redhat.hiperex.query.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import nl.flotsam.xeger.Xeger;

import org.apache.log4j.Logger;

import fi.muni.redhat.hiperex.jdbc.JDBCConnectionProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.Repeatable;

public class InsertJDBC implements Repeatable {

	private static Logger log = Logger.getLogger(InsertJDBC.class);
	private JDBCConnectionProvider connectionProvider = new JDBCConnectionProvider();
	private Xeger generator = new Xeger("[a-z]{10}");
	private StopWatch timer = new StopWatch();

	public long repeat() {
		long time = 0;
		String query = "INSERT INTO product(name) VALUES(?)";

		try {
			timer.start();
			Connection connection = connectionProvider.getJDBCConection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, generator.generate());
			ps.executeUpdate();
			connection.close();
			timer.stop();
			time = timer.getTime();
			timer.reset();
		} catch (SQLException e) {
			e.printStackTrace();
			timer.reset();
		}
		log.debug("Elapsed time of executing SQL insert: "+time);
		return time;
	}

}
