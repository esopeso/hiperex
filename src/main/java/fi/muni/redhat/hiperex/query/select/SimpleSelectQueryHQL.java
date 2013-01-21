package fi.muni.redhat.hiperex.query.select;

import fi.muni.redhat.hiperex.timer.StopWatch;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class SimpleSelectQueryHQL extends HibernateQuery implements Repeatable{
	
	private static Logger log = Logger.getLogger(SimpleSelectQueryHQL.class);
	private StopWatch timer = new StopWatch();

	public SimpleSelectQueryHQL(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
	}

	@SuppressWarnings("unused")
	public long repeat() {
		long time = 0;
		try {
			timer.start();
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery("FROM Customer WHERE customerId= :id");
			query.setParameter("id", 50);
			Customer customer = (Customer) query.uniqueResult();
			session.close();
			timer.stop();
			time = timer.getTime();
			log.debug("Elapsed time of mapping Customer by HQL: "+time);
			return time;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	
	

}
