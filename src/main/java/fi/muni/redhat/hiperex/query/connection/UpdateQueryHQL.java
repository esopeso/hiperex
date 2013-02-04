package fi.muni.redhat.hiperex.query.connection;

import java.util.HashMap;

import nl.flotsam.xeger.Xeger;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import fi.muni.redhat.hiperex.model.Product;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class UpdateQueryHQL extends HibernateQuery implements Repeatable{
	
	private static Logger log = Logger.getLogger(UpdateQueryHQL.class);
	private IdProvider idProvider;
	private Xeger generator = new Xeger("[a-z]{10}");

	public UpdateQueryHQL(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.idProvider = new IdProvider(factoryProvider, Product.class);
	}

	public HashMap<String, Long> execute() {
		Session session = null;
		Transaction tx = null;
		HashMap<String, Long> lapList = new HashMap<String, Long>();
		StopWatch timer = new StopWatch();
		String queryString = "UPDATE Product SET name = :name WHERE productId = :id";
		String name = generator.generate();
		int id = -1;
		
		try {
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			session = sessionFactory.getCurrentSession();
			timer.lap("connection");
			tx = session.getTransaction();
			tx.begin();
			Query query = session.createQuery(queryString);
			query.setString("name", name);
			query.setInteger("id", id);
			timer.lap("query");
			query.executeUpdate();
			tx.commit();
			timer.lap("execution");
			timer.stop();
			lapList = timer.getLapTimeList();
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (HibernateException he) {
				log.info("Couldn't roll back transaction");
			}
			e.printStackTrace();
			timer.stop();
		}
		if (lapList.get("overall") == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Estimated time of executing HQL update query: "+lapList);
		return lapList;
	}

}
