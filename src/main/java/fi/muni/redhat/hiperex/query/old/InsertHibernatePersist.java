package fi.muni.redhat.hiperex.query.old;

import nl.flotsam.xeger.Xeger;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import fi.muni.redhat.hiperex.model.Product;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.old.Repeatable;

public class InsertHibernatePersist extends HibernateQuery implements Repeatable {

	private static Logger log = Logger.getLogger(InsertHibernatePersist.class);
	private StopWatch timer = new StopWatch();
	private Xeger generator = new Xeger("[a-z]{10}");

	public InsertHibernatePersist(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
	}

	public long execute() {
		Session session = null;
		Transaction tx = null;
		long time = 0;

		try {
			timer.reset();
			timer.start();
			session = sessionFactory.getCurrentSession();
			tx = session.getTransaction();
			tx.begin();
			Product product = new Product();
			product.setName(generator.generate());
			session.save(product);
			tx.commit();
			timer.stop();
			time = timer.getTime();
		} catch (Exception e) {
			try {
				if (tx != null) tx.rollback();
			} catch (HibernateException he) {
				log.info("Couldn't roll back transaction");
			}
			e.printStackTrace();
			timer.reset();
		}
		if (time == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed time of persisiting new product: " + time);
		return time;
	}

}
