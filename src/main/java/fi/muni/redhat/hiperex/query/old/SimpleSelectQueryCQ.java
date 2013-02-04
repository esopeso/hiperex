package fi.muni.redhat.hiperex.query.old;

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
import fi.muni.redhat.hiperex.util.old.Repeatable;

public class SimpleSelectQueryCQ extends HibernateQuery implements Repeatable {

	private static Logger log = Logger.getLogger(SimpleSelectQueryCQ.class);
	private StopWatch timer = new StopWatch();
	private IdProvider idProvider;

	public SimpleSelectQueryCQ(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.idProvider = new IdProvider(factoryProvider, Customer.class);
	}

	@SuppressWarnings("unused")
	public long execute() {
		Session session = null;
		Transaction tx = null;
		int id = -1;
		long time = 0;
		
		try {
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			session = sessionFactory.getCurrentSession();
			tx = session.getTransaction();
			tx.begin();

			Criteria crit = session.createCriteria(Customer.class);
			crit.add(Restrictions.eq("customerId", id));
			Customer result = (Customer) crit.uniqueResult();

			tx.commit();
			timer.stop();
			time = timer.getTime();

		} catch (Exception ex) {
			try {
				tx.rollback();
			} catch (HibernateException he) {
				log.info("Couldn't roll back transaction");
			}
			ex.printStackTrace();
			timer.reset();
		}
		if (time == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed time of mapping Customer(50) by HC: " + time);
		return time;
	}

}
