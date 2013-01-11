package fi.muni.redhat.hiperex.query;

import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.hibernate.Query;
import org.hibernate.Session;

import fi.muni.redhat.hiperex.exception.EmptySetException;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class MultipleJoinQueryHQL extends HibernateQuery implements Repeatable{	

	private SessionFactoryProvider factoryProvider;

	public MultipleJoinQueryHQL(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.factoryProvider = factoryProvider;
	}

	@SuppressWarnings("unchecked")
	public long repeat() {
		long time = 0;
		StopWatch timer = new StopWatch();
		IdProvider idProvider = new IdProvider(factoryProvider, "SELECT DISTINCT customer.customerId FROM CustomerOrder");
		try {
		timer.start();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(
				"SELECT product.name " +
				"FROM ItemList AS list,CustomerOrder AS corder,Product AS product " +
				"WHERE corder.customer.customerId= :id " +
				"AND corder.orderId=list.customerOrder.orderId " +
				"AND list.product.productId=product.productId"
				);
		query.setParameter("id", idProvider.getRandomId());
		List<String> result = (List<String>)query.list();
		session.close();
		timer.stop();
		if (result.size()==0) throw new EmptySetException();
		time = timer.getTime();
		sessionFactory.getCache().evictQueryRegions();
		//System.out.println("Elapsed time of HQL query: "+time);
		return time;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return time;
	}

}
