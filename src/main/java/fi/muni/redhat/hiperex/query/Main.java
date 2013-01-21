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
import fi.muni.redhat.hiperex.jdbc.JDBCConnectionProvider;
import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.model.CustomerOrder;
import fi.muni.redhat.hiperex.model.Product;
import fi.muni.redhat.hiperex.query.delete.DeleteHQL;
import fi.muni.redhat.hiperex.query.delete.DeleteJDBC;
import fi.muni.redhat.hiperex.query.insert.HibernatePersist;
import fi.muni.redhat.hiperex.query.insert.InsertJDBC;
import fi.muni.redhat.hiperex.query.join.MultipleJoinQueryCQ;
import fi.muni.redhat.hiperex.query.join.MultipleJoinQueryHQL;
import fi.muni.redhat.hiperex.query.join.MultipleJoinQueryJDBC;
import fi.muni.redhat.hiperex.query.update.HibernateUpdate;
import fi.muni.redhat.hiperex.query.update.UpdateQueryHQL;
import fi.muni.redhat.hiperex.query.update.UpdateQueryJDBC;
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
		//SessionFactoryProvider factoryC3P0Provider = new SessionFactoryProvider("hibernate-c3p0.cfg.xml");
		//SessionFactoryProvider factoryBoneProvider = new SessionFactoryProvider("hibernate-boneCP.cfg.xml");
		
		//MultipleJoinQueryHQL query1 = new MultipleJoinQueryHQL(factoryProvider);
		//MultipleJoinQueryHQL query2 = new MultipleJoinQueryHQL(factoryC3P0Provider);
		//MultipleJoinQueryHQL query3 = new MultipleJoinQueryHQL(factoryBoneProvider);
		
		
		
		
		
		int n = 100;
		
		MultipleJoinQueryJDBC jdbc = new MultipleJoinQueryJDBC(factoryProvider);
		avgChartProcessor.addValue(avgDataProducer.cycle(n, jdbc), "JDBC", "join");
		
		MultipleJoinQueryHQL hql = new MultipleJoinQueryHQL(factoryProvider);
		avgChartProcessor.addValue(avgDataProducer.cycle(n, hql), "HQL", "join");
		
		MultipleJoinQueryCQ cq = new MultipleJoinQueryCQ(factoryProvider);
		avgChartProcessor.addValue(avgDataProducer.cycle(n, cq), "HC", "join");
		
		UpdateQueryJDBC updJDBC = new UpdateQueryJDBC(factoryProvider);
		avgChartProcessor.addValue(avgDataProducer.cycle(n, updJDBC), "JDBC", "update");
		
		UpdateQueryHQL updHQL = new UpdateQueryHQL(factoryProvider);
		avgChartProcessor.addValue(avgDataProducer.cycle(n, updHQL), "HQL", "update");
		
		HibernateUpdate objectPersist = new HibernateUpdate(factoryProvider);
		avgChartProcessor.addValue(avgDataProducer.cycle(n, objectPersist), "HP", "update");
		
		InsertJDBC insJDBC = new InsertJDBC();
		avgChartProcessor.addValue(avgDataProducer.cycle(n, insJDBC), "JDBC", "insert");
		
		HibernatePersist hiPer = new HibernatePersist(factoryProvider);
		avgChartProcessor.addValue(avgDataProducer.cycle(n, hiPer), "HP", "insert");
		
		DeleteHQL delHQL = new DeleteHQL(factoryProvider);
		avgChartProcessor.addValue(avgDataProducer.cycle(n, delHQL), "HQL", "delete");
		
		DeleteJDBC delJDBC = new DeleteJDBC(factoryProvider);
		avgChartProcessor.addValue(avgDataProducer.cycle(n, delJDBC), "JDBC", "delete");
		
		avgChartProcessor.produceChart("test.jpeg", "join comparison");
		
//		SimpleSelectQueryCQ ssqCQ = new SimpleSelectQueryCQ();
//		SimpleSelectQueryJDBC ssqJDBC = new SimpleSelectQueryJDBC();
		
//		SimpleSelectQueryHQL ssqHQL = new SimpleSelectQueryHQL(factoryProvider);
//		SimpleSelectQueryHQL ssqHQL1 = new SimpleSelectQueryHQL(factoryC3P0Provider);
//		SimpleSelectQueryHQL ssqHQL2 = new SimpleSelectQueryHQL(factoryBoneProvider);
		

//		xyChartProccessor.addSeries(xyDataProducer.cycle(10000, query1, "hibernateCP"));
//		xyChartProccessor.addSeries(xyDataProducer.cycle(10000, query2, "c3p0"));
//		xyChartProccessor.addSeries(xyDataProducer.cycle(10000, query3, "boneCP"));
//		xyChartProccessor.produceChart("hibernate-c3p0-bone-xy.jpeg", "connection-pooling");
		
//		avgDataProducer.cycle(5000, query1);
//		avgChartProcessor.addValue(avgDataProducer.cycle(5000, query1), "hibernateCP", "mjq query");
//		avgDataProducer.cycle(5000, query3);
//		avgChartProcessor.addValue(avgDataProducer.cycle(5000, query3), "boneCP", "mjq query");
//		avgDataProducer.cycle(5000, query2);
//		avgChartProcessor.addValue(avgDataProducer.cycle(5000, query2), "c3p0", "mjq query");
//		
//		
//		
//		avgChartProcessor.produceChart("hibernate-c3p0-bone.jpeg", "connection pooling");

//		
//		MultipleJoinQueryHQL mjqHQL = new MultipleJoinQueryHQL();
//		MultipleJoinQueryJDBC mjqJDBC = new MultipleJoinQueryJDBC();
//		chartProccessor.addSeries(dataProducer.cycle(100, mjqHQL, "multiple HQL join query"));
//		chartProccessor.addSeries(dataProducer.cycle(100, mjqJDBC, "multiple JDBC join query"));
//		chartProccessor.produceChart("hql-jdbc-join-query-static-id.jpeg","hql-jdbc-join-query-static-id");

	

	}
}
