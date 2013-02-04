package fi.muni.redhat.hiperex.query.connection;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class DeleteHQL extends HibernateQuery implements Repeatable {

	private static Logger log = Logger.getLogger(DeleteHQL.class);
	private IdProvider idProvider;
	private StopWatch timer = new StopWatch();

	public DeleteHQL(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.idProvider = new IdProvider(factoryProvider,
				"SELECT productId FROM Product WHERE productId>500");
	}

	public HashMap<String, Long> execute() {
		Session session = null;
		Transaction tx = null;
		HashMap<String, Long> lapList = new HashMap<String, Long>();
		int id = -1;
		
		try {
			String queryString = "DELETE FROM Product WHERE productId = :id";
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			
			session = sessionFactory.getCurrentSession();
			timer.lap("connection");
			tx = session.getTransaction();
			tx.begin();
			Query query = session.createQuery(queryString);
			query.setInteger("id", id);
			timer.lap("query");
			query.executeUpdate();
			tx.commit();
			timer.lap("execution");
			timer.stop();
			
			idProvider.removeId(id);
			lapList = timer.getLapTimeList();
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (HibernateException he) {
				log.info("Couldn't roll back transaction");
			}
			e.printStackTrace();
			timer.reset();
		}
		if (lapList.get("overall") == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed time of HQL delete query: " + lapList);
		return lapList;
	}

}
