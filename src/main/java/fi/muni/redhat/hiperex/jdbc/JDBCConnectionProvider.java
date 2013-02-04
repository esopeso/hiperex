package fi.muni.redhat.hiperex.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import fi.muni.redhat.hiperex.exception.NotUniqueIdException;
import fi.muni.redhat.hiperex.model.Customer;

public class JDBCConnectionProvider {

	private BoneCP bonePool = null;
	private ComboPooledDataSource c3P0Pool = null;

	public JDBCConnectionProvider() {
		createBoneCP();
		createC3p0Pool();
	}

	public Connection getJDBCConection() {
		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("MYSQL driver class not found");
			ex.printStackTrace();
			return connection;
		}
		// System.out.println("JDBC driver registered");

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/data", "root", "netreba");
		} catch (SQLException ex) {
			System.out.println("Connection to database failed");
			ex.printStackTrace();
			return connection;
		}
		return connection;
	}

	public void createBoneCP() {
		BoneCP connectionPool = null;
		try {
			// load the database driver (make sure this is in your classpath!)
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("MYSQL driver class not found");
			e.printStackTrace();
		}

		try {
			// setup the connection pool
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl("jdbc:mysql://localhost:3306/data");
			config.setUsername("root");
			config.setPassword("netreba");
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);
			config.setStatementsCacheSize(0);
			config.setAcquireIncrement(1);
			connectionPool = new BoneCP(config); // setup the connection pool
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.bonePool = connectionPool;
	}

	public Connection getBoneConnection() {
		try {
			return this.bonePool.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createC3p0Pool() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
		cpds.setDriverClass("com.mysql.jdbc.Driver");
		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/data");
		cpds.setUser("root");
		cpds.setPassword("netreba");
		cpds.setMinPoolSize(1);
		cpds.setMaxPoolSize(100);
		cpds.setMaxIdleTime(0);
		cpds.setMaxStatements(0);
		} catch (Exception e) {
			System.out.println("C3P0 create pool exception");
			e.printStackTrace();
		}
		this.c3P0Pool = cpds;
	}
	
	public Connection getC3P0Connection() throws SQLException {
		return this.c3P0Pool.getConnection();
	}

	public void testConnection() {
		Connection connection = getJDBCConection();
		if (connection != null) {
			System.out.println("DB connection established");
			try {
				connection.close();
				System.out.println("DB connection closed");
			} catch (SQLException ex) {
				System.out.println("Close connection failed");
				ex.printStackTrace();
			}
		} else {
			System.out.println("DB connection failed");
		}
	}

	public Customer getLazyCustomerByID(int id) throws NotUniqueIdException,
			SQLException {
		int i = 0;
		Customer customer = new Customer();
		ResultSet rs;
		String query = "SELECT * FROM customer WHERE customer_id = ?";

		Connection connection = getJDBCConection();
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, id);
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
		return customer;
	}

	public void insertCustomer(Customer customer) throws SQLException {
		String query = "INSERT INTO customer(first_name,last_name,birth_date,phone_number,address) VALUES(?,?,?,?,?)";

		Connection connection = getJDBCConection();
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, customer.getFirstName());
		ps.setString(2, customer.getLastName());
		ps.setDate(3, (Date) customer.getBirthDate());
		ps.setString(4, customer.getPhoneNumber());
		ps.setString(5, customer.getAddress());
		ps.executeUpdate();
		connection.close();
	}
}
