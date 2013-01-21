package fi.muni.redhat.hiperex.query.delete;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

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

	public long repeat() {
		long time = 0;
		
		try {
			timer.start();
			Session session = sessionFactory.getCurrentSession();			
			int id = idProvider.getRandomId();			
			session.getTransaction().begin();	
			Query query = session.createQuery("DELETE FROM Product WHERE productId = :id");
			query.setInteger("id", id);
			query.executeUpdate();
			session.getTransaction().commit();
			timer.stop();
			time = timer.getTime();
			idProvider.removeId(id);
		} catch (Exception e) {
			e.printStackTrace();
			timer.reset();
		} 
		timer.reset();
		log.debug("Elapsed time of HQL delete query: " + time);
		return time;
	}

}
