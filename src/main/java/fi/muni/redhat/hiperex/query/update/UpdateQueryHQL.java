package fi.muni.redhat.hiperex.query.update;

import nl.flotsam.xeger.Xeger;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

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

	public long repeat() {
		long time = 0;
		StopWatch timer = new StopWatch();
		String queryString = "UPDATE Product SET name = :name WHERE productId = :id";
		String name = generator.generate();
		try {
			timer.start();
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setString("name", name);
			query.setInteger("id", idProvider.getRandomId());
			query.executeUpdate();
			session.close();
			timer.stop();
			time = timer.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			timer.stop();
		}
		log.debug("Estimated time of executing HQL update query: "+time);
		return time;
	}

}
