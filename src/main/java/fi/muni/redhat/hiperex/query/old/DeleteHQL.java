package fi.muni.redhat.hiperex.query.old;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.old.Repeatable;

public class DeleteHQL extends HibernateQuery implements Repeatable {

	private static Logger log = Logger.getLogger(DeleteHQL.class);
	private IdProvider idProvider;
	private StopWatch timer = new StopWatch();

	public DeleteHQL(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.idProvider = new IdProvider(factoryProvider,
				"SELECT productId FROM Product WHERE productId>500");
	}

	public long execute() {
		Session session = null;
		Transaction tx = null;
		long time = 0;
		int id = -1;
		
		try {
			String queryString = "DELETE FROM Product WHERE productId = :id";
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			
			session = sessionFactory.getCurrentSession();
			
			tx = session.getTransaction();
			tx.begin();
			Query query = session.createQuery(queryString);
			query.setInteger("id", id);
			query.executeUpdate();
			tx.commit();
			timer.stop();
			
			idProvider.removeId(id);
			time = timer.getTime();
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (HibernateException he) {
				log.info("Couldn't roll back transaction");
			}
			e.printStackTrace();
			timer.reset();
		}
		if (time == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed time of HQL delete query: " + time);
		return time;
	}

}
