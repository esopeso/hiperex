package fi.muni.redhat.hiperex.query.connection;

import java.util.HashMap;

import fi.muni.redhat.hiperex.timer.StopWatch;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class SimpleSelectQueryCQ extends HibernateQuery implements Repeatable {

	private static Logger log = Logger.getLogger(SimpleSelectQueryCQ.class);
	private StopWatch timer = new StopWatch();
	private IdProvider idProvider;

	public SimpleSelectQueryCQ(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.idProvider = new IdProvider(factoryProvider, Customer.class);
	}

	@SuppressWarnings("unused")
	public HashMap<String, Long> execute() {
		Session session = null;
		Transaction tx = null;
		int id = -1;
		HashMap<String, Long> lapList = new HashMap<String, Long>();
		
		try {
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			session = sessionFactory.getCurrentSession();
			timer.lap("connection");
			tx = session.getTransaction();
			tx.begin();

			Criteria crit = session.createCriteria(Customer.class);
			crit.add(Restrictions.eq("customerId", id));
			timer.lap("query");
			Customer result = (Customer) crit.uniqueResult();

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
		log.debug("Elapsed time of mapping Customer(50) by HC: " + lapList);
		return lapList;
	}

}
