package fi.muni.redhat.hiperex.query;


import fi.muni.redhat.hiperex.timer.StopWatch;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class SimpleSelectQueryCQ extends HibernateQuery implements Repeatable{
	
	private static Logger log = Logger.getLogger(SimpleSelectQueryCQ.class);
	
	public SimpleSelectQueryCQ(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
	}

	@SuppressWarnings("unused")
	public long repeat() {
		try {
			long time = 0;
			StopWatch timer = new StopWatch();
			timer.start();
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Criteria crit = session.createCriteria(Customer.class);
			crit.add(Restrictions.eq("customerId", 50));
			Customer result = (Customer)crit.uniqueResult();
			session.close();
			timer.stop();
			time = timer.getTime();
			log.debug("Elapsed time of mapping Customer(50) by HC: "+time);
			return time;
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;	
	}
	

}
