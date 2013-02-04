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
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.old.Repeatable;

public class UpdateQueryHibernate extends HibernateQuery implements Repeatable {

	private static Logger log = Logger.getLogger(UpdateQueryHibernate.class);
	private IdProvider idProvider;
	private Xeger generator = new Xeger("[a-z]{10}");

	public UpdateQueryHibernate(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.idProvider = new IdProvider(factoryProvider, Product.class);
	}

	public long execute() {
		Session session = null;
		Transaction tx = null;
		long time = 0;
		StopWatch timer = new StopWatch();
		int id = -1;
		

		try {
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			
			session = sessionFactory.getCurrentSession();
			tx = session.getTransaction();
			tx.begin();
			Product product = (Product) session.get(Product.class, id);
			product.setName(generator.generate());
			session.saveOrUpdate(product);
			tx.commit();
			timer.stop();
			time = timer.getTime();
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (HibernateException he) {
				log.info("Couldn't roll back transaction");
			}
			e.printStackTrace();
			timer.stop();
		}
		if (time == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed time of update: " + time);
		return time;
	}

}
