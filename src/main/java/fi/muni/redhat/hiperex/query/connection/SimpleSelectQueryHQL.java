package fi.muni.redhat.hiperex.query.connection;

import java.util.HashMap;
import java.util.Map;

import fi.muni.redhat.hiperex.timer.StopWatch;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class SimpleSelectQueryHQL extends HibernateQuery implements Repeatable{
	
	private static Logger log = Logger.getLogger(SimpleSelectQueryHQL.class);
	private StopWatch timer = new StopWatch();
	private IdProvider idProvider;

	public SimpleSelectQueryHQL(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.idProvider = new IdProvider(factoryProvider, Customer.class);
	}

	@SuppressWarnings("unused")
	public HashMap<String, Long> execute() {
		Session session = null;
		Transaction tx = null;
		String queryString = "FROM Customer WHERE customerId= :id";
		HashMap<String, Long> lapList = new HashMap<String, Long>();
		int id = -1;
		Customer customer = null;
		
		try {
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			session = sessionFactory.getCurrentSession();
			timer.lap("connection");
			tx = session.getTransaction();
			tx.begin();
			
			Query query = session.createQuery(queryString);
			query.setParameter("id", id);
			timer.lap("query");
			customer = (Customer) query.uniqueResult();
			
			tx.commit();
			timer.lap("execution");
			timer.stop();
			lapList = timer.getLapTimeList();
			
		} catch (Exception ex) {
			try {
				tx.rollback();
			} catch (HibernateException he) {
				log.info("Couldn't roll back transaction");
			}
			ex.printStackTrace();
			timer.reset();
		}
		if (lapList.get("overall") == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed time of mapping Customer by HQL: "+lapList);
		return lapList;
	}
	
	

}
