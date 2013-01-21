package fi.muni.redhat.hiperex.query.update;

import nl.flotsam.xeger.Xeger;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import fi.muni.redhat.hiperex.model.Product;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class HibernateUpdate extends HibernateQuery implements Repeatable{

	private static Logger log = Logger.getLogger(HibernateUpdate.class);
	private IdProvider idProvider;
	private Xeger generator = new Xeger("[a-z]{10}");
	
	public HibernateUpdate(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.idProvider = new IdProvider(factoryProvider, Product.class);
	}

	public long repeat() {
		long time = 0;
		StopWatch timer = new StopWatch();
		
		try {
			timer.start();
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Product product = (Product) session.get(Product.class, idProvider.getRandomId());
			product.setName(generator.generate());
			session.saveOrUpdate(product);
			session.close();
			timer.stop();
			time = timer.getTime();
			
		} catch (Exception e) {
			e.printStackTrace();
			timer.stop();
		}
		log.debug("Elapsed time of update: "+time);
		return time;
	}

}
