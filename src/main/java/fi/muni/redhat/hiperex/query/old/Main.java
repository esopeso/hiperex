package fi.muni.redhat.hiperex.query.old;

import java.io.IOException;
import java.sql.SQLException;

import fi.muni.redhat.hiperex.exception.NotUniqueIdException;
import fi.muni.redhat.hiperex.jdbc.JDBCConnectionProvider;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.util.old.AvgBarChartProcessor;
import fi.muni.redhat.hiperex.util.old.DataProducer;
import fi.muni.redhat.hiperex.util.old.XYChartProcessor;

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
		AvgBarChartProcessor avgChartProcessor = new AvgBarChartProcessor();
		DataProducer dataProducer = new DataProducer();
		
		//SessionFactoryProvider factoryProvider = new SessionFactoryProvider("hibernate.cfg.xml");
		JDBCConnectionProvider jdbcProvider = new JDBCConnectionProvider();
		SessionFactoryProvider factoryProvider = new SessionFactoryProvider("hibernate-c3p0.cfg.xml");
		//SessionFactoryProvider factoryProvider = new SessionFactoryProvider("hibernate-boneCP.cfg.xml");
		
		
		int n = 1000;
		SimpleSelectQueryCQ ssqCQ = new SimpleSelectQueryCQ(factoryProvider);
		ssqCQ.execute();
		dataProducer.cycle(n, ssqCQ);
		//barChartProcessor.addValue(time, category, series)
		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HC", "select");
		xyChartProccessor.addSeries(dataProducer.getXYSeries("CQ"));
		xyChartProccessor.produceChart("xy-select-HC.jpeg-1000-old.jpeg", "Simple select query");
		
		SimpleSelectQueryHQL ssqHQL = new SimpleSelectQueryHQL(factoryProvider);
		ssqHQL.execute();
		dataProducer.cycle(n, ssqHQL);
		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HQL", "select");
		xyChartProccessor.addSeries(dataProducer.getXYSeries("HQL"));
		xyChartProccessor.produceChart("xy-select-HQL.jpeg-1000-old.jpeg", "Simple select query");
		
		SimpleSelectQueryJDBC ssqJDBC = new SimpleSelectQueryJDBC(jdbcProvider,factoryProvider);
		ssqJDBC.execute();
		dataProducer.cycle(n, ssqJDBC);
		avgChartProcessor.addValue(dataProducer.getAverageTime(), "JDBC", "select");
		xyChartProccessor.addSeries(dataProducer.getXYSeries("JDBC"));
		xyChartProccessor.produceChart("xy-select-JDBC.jpeg-1000-old.jpeg", "Simple select query");
		
		avgChartProcessor.produceChart("avg-select-1000-old.jpeg", "Simple select query");
		
			
		
		
//		MultipleJoinQueryJDBC jdbc = new MultipleJoinQueryJDBC(jdbcProvider ,factoryProvider);
//		jdbc.execute();
//		dataProducer.cycle(n, jdbc);
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "JDBC", "join");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("JDBC"));
//		xyChartProccessor.produceChart("xy-join-JDBC.jpeg-1000.jpeg", "Join over 3 tables");
//		
//		MultipleJoinQueryHQL hql = new MultipleJoinQueryHQL(factoryProvider);
//		hql.execute();
//		dataProducer.cycle(n, hql);
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HQL", "join");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("HQL"));
//		xyChartProccessor.produceChart("xy-join-HQL.jpeg-1000.jpeg", "Join over 3 tables");
//		
//		MultipleJoinQueryCQ cq = new MultipleJoinQueryCQ(factoryProvider);
//		cq.execute();
//		dataProducer.cycle(n, cq);
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HC", "join");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("HC"));
//		xyChartProccessor.produceChart("xy-join-HC.jpeg-1000.jpeg", "Join over 3 tables");
//		
//		avgChartProcessor.produceChart("avg-join-1000.jpeg", "Join over 3 tables");
//		
//		
//		
//		UpdateQueryJDBC updJDBC = new UpdateQueryJDBC(jdbcProvider,factoryProvider);
//		updJDBC.execute();
//		dataProducer.cycle(n, updJDBC);
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "JDBC", "update");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("JDBC"));
//		xyChartProccessor.produceChart("xy-update-JDBC.jpeg-1000.jpeg", "Simple update query");
//		
//		UpdateQueryHQL updHQL = new UpdateQueryHQL(factoryProvider);
//		updHQL.execute();
//		dataProducer.cycle(n, updHQL);
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HQL", "update");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("HQL"));
//		xyChartProccessor.produceChart("xy-update-HQL.jpeg-1000.jpeg", "Simple update query");
//		
//		UpdateQueryHibernate objectPersist = new UpdateQueryHibernate(factoryProvider);
//		updHQL.execute();
//		dataProducer.cycle(n, objectPersist);
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HP", "update");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("HP"));
//		xyChartProccessor.produceChart("xy-update-HP.jpeg-1000.jpeg", "Simple update query");
//		
//		avgChartProcessor.produceChart("avg-update-1000.jpeg", "Simple update query");
//		
//		
//		
//		InsertJDBC insJDBC = new InsertJDBC(jdbcProvider);
//		insJDBC.execute();
//		dataProducer.cycle(n, insJDBC);
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "JDBC", "insert");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("JDBC"));
//		xyChartProccessor.produceChart("xy-insert-JDBC.jpeg-1000.jpeg", "Simple insert query");
//		
//		InsertHibernatePersist hiPer = new InsertHibernatePersist(factoryProvider);
//		hiPer.execute();
//		dataProducer.cycle(n, hiPer);
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HP", "insert");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("HP"));
//		xyChartProccessor.produceChart("xy-insert-HP.jpeg-1000.jpeg", "Simple insert query");
//		
//		avgChartProcessor.produceChart("avg-insert-1000.jpeg", "Simple insert query");
//		
//		
//		DeleteHQL delHQL = new DeleteHQL(factoryProvider);
//		delHQL.execute();
//		dataProducer.cycle(n, delHQL);
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "HQL", "delete");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("HQL"));
//		xyChartProccessor.produceChart("xy-delete-HQL.jpeg-1000.jpeg", "Simple delete query");
//		
//		DeleteJDBC delJDBC = new DeleteJDBC(jdbcProvider,factoryProvider);
//		delJDBC.execute();
//		dataProducer.cycle(n, delJDBC);
//		avgChartProcessor.addValue(dataProducer.getAverageTime(), "JDBC", "delete");
//		xyChartProccessor.addSeries(dataProducer.getXYSeries("JDBC"));
//		xyChartProccessor.produceChart("xy-delete-JDBC.jpeg-1000.jpeg", "Simple delete query");
//		
//		avgChartProcessor.produceChart("avg-delete-1000.jpeg", "comparison");


	

	}
}
