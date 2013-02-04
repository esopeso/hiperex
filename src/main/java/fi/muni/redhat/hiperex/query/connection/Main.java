package fi.muni.redhat.hiperex.query.connection;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;

import fi.muni.redhat.hiperex.exception.NotUniqueIdException;
import fi.muni.redhat.hiperex.jdbc.JDBCConnectionProvider;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.util.AvgBarChartProcessor;
import fi.muni.redhat.hiperex.util.DataProducer;
import fi.muni.redhat.hiperex.util.GroupedStackedBarChartProcessor;
import fi.muni.redhat.hiperex.util.StackedBarChartProcessor;
import fi.muni.redhat.hiperex.util.XYChartProcessor;

public class Main {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws NotUniqueIdException
	 * @throws IOException
	 * @throws  
	 */
	public static void main(String[] args) throws NotUniqueIdException, SQLException, IOException {
		
		
		XYChartProcessor xyChartProccessor = new XYChartProcessor();
		xyChartProccessor.setResolution(1920, 1080);
		AvgBarChartProcessor avgChartProcessor = new AvgBarChartProcessor();
		StackedBarChartProcessor barChartProcessor = new StackedBarChartProcessor();
		DataProducer dataProducer = new DataProducer();
		
		//SessionFactoryProvider factoryProvider = new SessionFactoryProvider("hibernate.cfg.xml");
		JDBCConnectionProvider jdbcProvider = new JDBCConnectionProvider();
		SessionFactoryProvider factoryProvider = new SessionFactoryProvider("hibernate-c3p0.cfg.xml");
		//SessionFactoryProvider factoryProvider = new SessionFactoryProvider("hibernate-boneCP.cfg.xml");
		
		
		int n = 1;
		
		
//		//Simple select query CQ
//		SimpleSelectQueryCQ ssqCQ = new SimpleSelectQueryCQ(factoryProvider);
//		ssqCQ.execute();
//		dataProducer.cycle(n, ssqCQ);
//		
//		dataProducer.calculateAvgLapResults();
//		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "CQ");
//		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "CQ");
//		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "CQ");
//		
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HC", "select");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("CQ"));
//		xyChartProccessor.produceChart("xy-select-HC.jpeg-1000.jpeg", "Simple select query");
//		
//		
//		//Simple select query HQL
//		SimpleSelectQueryHQL ssqHQL = new SimpleSelectQueryHQL(factoryProvider);
//		ssqHQL.execute();
//		dataProducer.cycle(n, ssqHQL);
//		
//		dataProducer.calculateAvgLapResults();
//		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "HQL");
//		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "HQL");
//		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "HQL");
//		
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HQL", "select");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("HQL"));
//		xyChartProccessor.produceChart("xy-select-HQL.jpeg-1000.jpeg", "Simple select query");
//		
//		
//		//Simple select query JDBC
//		SimpleSelectQueryJDBC ssqJDBC = new SimpleSelectQueryJDBC(jdbcProvider,factoryProvider);
//		ssqJDBC.execute();
//		dataProducer.cycle(n, ssqJDBC);
//		
//		dataProducer.calculateAvgLapResults();
//		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "JDBC");
//		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "JDBC");
//		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "JDBC");
//		
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "JDBC", "select");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("JDBC"));
//		xyChartProccessor.produceChart("xy-select-JDBC.jpeg-1000.jpeg", "Simple select query");
//		
//		
//		avgChartProcessor.produceChart("avg-select-1000.jpeg", "Simple select query");
//		barChartProcessor.produceChart("avg-stacked-select-1000.jpg", "Simple stacked select query");
//		
//			
//		
//		//Multiple join query JDBC
//		MultipleJoinQueryJDBC jdbc = new MultipleJoinQueryJDBC(jdbcProvider ,factoryProvider);
//		jdbc.execute();
//		dataProducer.cycle(n, jdbc);
//		
//		dataProducer.calculateAvgLapResults();
//		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "JDBC");
//		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "JDBC");
//		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "JDBC");
//		
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "JDBC", "join");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("JDBC"));
//		xyChartProccessor.produceChart("xy-join-JDBC.jpeg-1000.jpeg", "Join over 3 tables");
		
		
		//Multiple join query HQL
		MultipleJoinQueryHQL hql = new MultipleJoinQueryHQL(factoryProvider);
		hql.execute();
		dataProducer.cycle(n, hql);
		
		dataProducer.calculateAvgLapResults();
		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "HQL");
		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "HQL");
		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "HQL");
		
		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HQL", "join");
		xyChartProccessor.addSeries(dataProducer.getXYSeries("HQL"));
		xyChartProccessor.produceChart("xy-join-HQL.jpeg-1000.jpeg", "Join over 3 tables");
//		
//		
//		//Multiple join query CQ
//		MultipleJoinQueryCQ cq = new MultipleJoinQueryCQ(factoryProvider);
//		cq.execute();
//		dataProducer.cycle(n, cq);
//		
//		dataProducer.calculateAvgLapResults();
//		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "CQ");
//		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "CQ");
//		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "CQ");
//		
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HC", "join");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("HC"));
//		xyChartProccessor.produceChart("xy-join-HC.jpeg-1000.jpeg", "Join over 3 tables");
//		
//		avgChartProcessor.produceChart("avg-join-1000.jpeg", "Join over 3 tables");
//		barChartProcessor.produceChart("avg-stacked-join-1000.jpg", "Stacked join query");
//		
//		
//		//Upadate query JDBC
//		UpdateQueryJDBC updJDBC = new UpdateQueryJDBC(jdbcProvider,factoryProvider);
//		updJDBC.execute();
//		dataProducer.cycle(n, updJDBC);
//		
//		dataProducer.calculateAvgLapResults();
//		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "JDBC");
//		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "JDBC");
//		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "JDBC");
//		
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "JDBC", "update");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("JDBC"));
//		xyChartProccessor.produceChart("xy-update-JDBC.jpeg-1000.jpeg", "Simple update query");
//		
//		// Update query HQL
//		UpdateQueryHQL updHQL = new UpdateQueryHQL(factoryProvider);
//		updHQL.execute();
//		dataProducer.cycle(n, updHQL);
//		
//		dataProducer.calculateAvgLapResults();
//		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "HQL");
//		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "HQL");
//		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "HQL");
//		
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HQL", "update");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("HQL"));
//		xyChartProccessor.produceChart("xy-update-HQL.jpeg-1000.jpeg", "Simple update query");
//		
//		//Update query hibernate
//		UpdateQueryHibernate objectPersist = new UpdateQueryHibernate(factoryProvider);
//		updHQL.execute();
//		dataProducer.cycle(n, objectPersist);
//		
//		dataProducer.calculateAvgLapResults();
//		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "Hibernate");
//		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "Hibernate");
//		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "Hibernate");
//		
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HP", "update");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("HP"));
//		xyChartProccessor.produceChart("xy-update-HP.jpeg-1000.jpeg", "Simple update query");
//		
//		avgChartProcessor.produceChart("avg-update-1000.jpeg", "Simple update query");
//		barChartProcessor.produceChart("avg-stacked-update-1000.jpg", "Stacked update query");
//
//		//Insert query JDBC
//		InsertJDBC insJDBC = new InsertJDBC(jdbcProvider);
//		insJDBC.execute();
//		dataProducer.cycle(n, insJDBC);
//		
//		dataProducer.calculateAvgLapResults();
//		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "JDBC");
//		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "JDBC");
//		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "JDBC");
//		
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "JDBC", "insert");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("JDBC"));
//		xyChartProccessor.produceChart("xy-insert-JDBC.jpeg-1000.jpeg", "Simple insert query");
//		
//		
//		//Insert query hibernate
//		InsertHibernatePersist hiPer = new InsertHibernatePersist(factoryProvider);
//		hiPer.execute();
//		dataProducer.cycle(n, hiPer);
//		
//		dataProducer.calculateAvgLapResults();
//		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "Hibernate");
//		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "Hibernate");
//		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "Hibernate");
//		
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HP", "insert");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("HP"));
//		xyChartProccessor.produceChart("xy-insert-HP.jpeg-1000.jpeg", "Simple insert query");
//		
//		avgChartProcessor.produceChart("avg-insert-1000.jpeg", "Simple insert query");
//		barChartProcessor.produceChart("avg-stacked-insert-1000.jpg", "Stacked insert query");
//		
//		//Delete query HQL
//		DeleteHQL delHQL = new DeleteHQL(factoryProvider);
//		delHQL.execute();
//		dataProducer.cycle(n, delHQL);
//		
//		dataProducer.calculateAvgLapResults();
//		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "HQL");
//		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "HQL");
//		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "HQL");
//		
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HQL", "delete");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("HQL"));
//		xyChartProccessor.produceChart("xy-delete-HQL.jpeg-1000.jpeg", "Simple delete query");
//		
//		//Delete query JDBC
//		DeleteJDBC delJDBC = new DeleteJDBC(jdbcProvider,factoryProvider);
//		delJDBC.execute();
//		dataProducer.cycle(n, delJDBC);
//		
//		dataProducer.calculateAvgLapResults();
//		barChartProcessor.addValue(dataProducer.getLapAverage("connection"), "connection", "JDBC");
//		barChartProcessor.addValue(dataProducer.getLapAverage("query"), "query", "JDBC");
//		barChartProcessor.addValue(dataProducer.getLapAverage("execution"), "execution", "JDBC");
//		
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "JDBC", "delete");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("JDBC"));
//		xyChartProccessor.produceChart("xy-delete-JDBC.jpeg-1000.jpeg", "Simple delete query");
//		
//		avgChartProcessor.produceChart("avg-delete-1000.jpeg", "comparison");
//		barChartProcessor.produceChart("avg-stacked-delete-1000.jpg", "Stacked delete query");


	

	}
}
