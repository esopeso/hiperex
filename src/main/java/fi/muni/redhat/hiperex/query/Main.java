package fi.muni.redhat.hiperex.query;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import fi.muni.redhat.hiperex.chart.ChartGenerator;
import fi.muni.redhat.hiperex.exception.NotUniqueIdException;
import fi.muni.redhat.hiperex.jdbc.JDBCConnection;
import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.model.CustomerOrder;
import fi.muni.redhat.hiperex.model.Product;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.util.AvgDataProducer;
import fi.muni.redhat.hiperex.util.AvgBarChartProcessor;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.XYChartProcessor;
import fi.muni.redhat.hiperex.util.XYDataProducer;

public class Main {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws NotUniqueIdException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws NotUniqueIdException, SQLException, IOException {
		
		/**
		 * produces xy chart 
		 */
		XYDataProducer xyDataProducer = new XYDataProducer();
		XYChartProcessor xyChartProccessor = new XYChartProcessor();
		AvgDataProducer avgDataProducer = new AvgDataProducer();
		AvgBarChartProcessor avgChartProcessor = new AvgBarChartProcessor();
		
		SessionFactoryProvider factoryProvider = new SessionFactoryProvider("hibernate.cfg.xml");
		SessionFactoryProvider factoryC3P0Provider = new SessionFactoryProvider("hibernate-c3p0.cfg.xml");
		
//		SimpleSelectQueryCQ ssqCQ = new SimpleSelectQueryCQ();
//		SimpleSelectQueryJDBC ssqJDBC = new SimpleSelectQueryJDBC();
		
		SimpleSelectQueryHQL ssqHQL = new SimpleSelectQueryHQL(factoryProvider);
		SimpleSelectQueryHQL ssqHQL1 = new SimpleSelectQueryHQL(factoryC3P0Provider);
		

//		xyChartProccessor.addSeries(xyDataProducer.cycle(500, ssqHQL, "hql query"));
//		xyChartProccessor.addSeries(xyDataProducer.cycle(500, ssqHQL1, "hql query with c3p0 pooling"));
//		xyChartProccessor.produceChart("hibernate-vs-c3p0-pooling.jpeg", "connection-pooling");
		
		avgChartProcessor.addValue(avgDataProducer.cycle(50000, ssqHQL), "default", "simple query");
		avgChartProcessor.addValue(avgDataProducer.cycle(50000, ssqHQL1), "c3p0", "simple query");
		avgChartProcessor.produceChart("hibernate-vs-c3p0-avg.jpeg", "default-vs-c3p0-pooling");

//		
//		MultipleJoinQueryHQL mjqHQL = new MultipleJoinQueryHQL();
//		MultipleJoinQueryJDBC mjqJDBC = new MultipleJoinQueryJDBC();
//		chartProccessor.addSeries(dataProducer.cycle(100, mjqHQL, "multiple HQL join query"));
//		chartProccessor.addSeries(dataProducer.cycle(100, mjqJDBC, "multiple JDBC join query"));
//		chartProccessor.produceChart("hql-jdbc-join-query-static-id.jpeg","hql-jdbc-join-query-static-id");
		
	
//		avgChartProcessor.addValue(avgDataProducer.cycle(1000, ssqHQL), "HQL", "serie1");
//		avgChartProcessor.addValue(avgDataProducer.cycle(1000, ssqJDBC), "JDBC", "serie1");
//		avgChartProcessor.produceChart("hql-jdbc-simple-avg-chart.jpeg", "hql-jdbc-simple-avg-chart");


		
		
		
		
//		JDBCConnection jdbcc = new JDBCConnection();
//		jdbcc.testConnection();
//		Customer customer = jdbcc.getLazyCustomerByID(20);
//		System.out.println(customer.getFirstName()+" "+customer.getLastName());
//		customer.setFirstName("Kelly");
//		jdbcc.insertCustomer(customer);
//		
//		tq.persist(customer);
		
//		customer.setPhoneNumber("0904365023");
//		tq.saveOrUpdate(customer);
		
//		long maxExecuteTime;
//		long minExecuteTime = 0;
//		
//		maxExecuteTime = stat
//				.getQueryStatistics("select customer0_.customer_id as customer1_0_0_, customer0_.first_name as first2_0_0_, customer0_.last_name as last3_0_0_, customer0_.birth_date as birth4_0_0_, customer0_.phone_number as phone5_0_0_, customer0_.address as address0_0_ from data.customer customer0_ where customer0_.customer_id=? FROM Customer WHERE customerId= :id")
//				.getExecutionMaxTime();		
//		System.out.println("Maximum execution time: "+maxExecuteTime);
//		
//		minExecuteTime = stat
//				.getQueryStatistics("select customer0_.customer_id as customer1_0_0_, customer0_.first_name as first2_0_0_, customer0_.last_name as last3_0_0_, customer0_.birth_date as birth4_0_0_, customer0_.phone_number as phone5_0_0_, customer0_.address as address0_0_ from data.customer customer0_ where customer0_.customer_id=? FROM Customer WHERE customerId= :id")
//				.getExecutionMinTime();		
//		System.out.println("Minimum execution time: "+minExecuteTime);
//		
//		
//		System.out.println("Connection count: "+stat.getConnectCount());
//		System.out.println("Transaction count: "+stat.getTransactionCount());
//		System.out.println("Opened session count:"+stat.getSessionOpenCount());
//		System.out.println("Closes session count: "+stat.getSessionCloseCount());
//		String[] exQueries = stat.getQueries();
//		System.out.println("Executed queries: ");
//		for (int i=0; i<exQueries.length; i++) {
//			System.out.println(exQueries[i]);
//		}	

	}

}
