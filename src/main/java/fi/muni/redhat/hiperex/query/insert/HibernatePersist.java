package fi.muni.redhat.hiperex.query.insert;

import nl.flotsam.xeger.Xeger;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import fi.muni.redhat.hiperex.model.Product;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.Repeatable;

public class HibernatePersist extends HibernateQuery implements Repeatable{
	
	private static Logger log = Logger.getLogger(HibernatePersist.class);
	private StopWatch timer = new StopWatch();
	private Xeger generator = new Xeger("[a-z]{10}");

	public HibernatePersist(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
	}

	public long repeat() {
		long time = 0;
		
		timer.start();
		Session session = sessionFactory.getCurrentSession();	
		session.getTransaction().begin();
		Product product = new Product();
		product.setName(generator.generate());
		session.save(product);
		session.getTransaction().commit();
		timer.stop();
		time = timer.getTime();
		timer.reset();
		log.debug("Elapsed time of persisiting new product: "+time);
		return time;
	}

}
