package fi.muni.redhat.hiperex.query.connection;

import java.util.HashMap;

import nl.flotsam.xeger.Xeger;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import fi.muni.redhat.hiperex.model.Product;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.Repeatable;

public class InsertHibernatePersist extends HibernateQuery implements Repeatable {

	private static Logger log = Logger.getLogger(InsertHibernatePersist.class);
	private StopWatch timer = new StopWatch();
	private Xeger generator = new Xeger("[a-z]{10}");

	public InsertHibernatePersist(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
	}

	public HashMap<String, Long> execute() {
		Session session = null;
		Transaction tx = null;
		HashMap<String, Long> lapList = new HashMap<String, Long>();

		try {
			timer.reset();
			timer.start();
			session = sessionFactory.getCurrentSession();
			timer.lap("connection");
			tx = session.getTransaction();
			tx.begin();
			Product product = new Product();
			product.setName(generator.generate());
			timer.lap("query");
			session.save(product);
			tx.commit();
			timer.lap("execution");
			timer.stop();
			lapList = timer.getLapTimeList();
		} catch (Exception e) {
			try {
				if (tx != null) tx.rollback();
			} catch (HibernateException he) {
				log.info("Couldn't roll back transaction");
			}
			e.printStackTrace();
			timer.reset();
		}
		if (lapList.get("overall") == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed time of persisiting new product: " + lapList);
		return lapList;
	}

}
