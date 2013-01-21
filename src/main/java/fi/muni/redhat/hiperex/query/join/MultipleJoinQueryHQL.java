package fi.muni.redhat.hiperex.query.join;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import fi.muni.redhat.hiperex.exception.EmptySetException;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class MultipleJoinQueryHQL extends HibernateQuery implements Repeatable {

	private static Logger log = Logger.getLogger(MultipleJoinQueryHQL.class);
	// private SessionFactoryProvider factoryProvider;
	private IdProvider idProvider;
	private StopWatch timer = new StopWatch();

	/**
	 * Invokes SessionFactory from given SessionFactorProvider and provides
	 * Class to IdProvider. IDs will be queried from specified class.
	 * 
	 * @param factoryProvider
	 *            SessionFactoryProvider that provides SessionFactory
	 * @param clazz
	 *            IDs of this mapped class will be provided by idProvider
	 */
	@SuppressWarnings("rawtypes")
	public MultipleJoinQueryHQL(SessionFactoryProvider factoryProvider,
			Class clazz) {
		super(factoryProvider);
		// this.factoryProvider = factoryProvider;
		this.idProvider = new IdProvider(factoryProvider, clazz);
	}

	/**
	 * Invokes SessionFactory from given SessionFactorProvider and provides SQL
	 * query to IdProvider
	 * 
	 * @param factoryProvider
	 *            SessionFactoryProvider that provides SessionFactory
	 */
	public MultipleJoinQueryHQL(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.idProvider = new IdProvider(factoryProvider,
				"SELECT DISTINCT customer.customerId FROM CustomerOrder");
	}

	@SuppressWarnings("unchecked")
	public long repeat() {
		long time = 0;
		// HQL subqueries can occur only in the select or where clauses
		String queryString = "SELECT DISTINCT product.name "
				+ "FROM ItemList AS list,CustomerOrder AS corder,Product AS product "
				+ "WHERE corder.customer.customerId= :id "
				+ "AND corder.orderId=list.customerOrder.orderId "
				+ "AND list.product.productId=product.productId";

		try {
			timer.start();
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(queryString);
			query.setParameter("id", idProvider.getRandomId());
			List<String> result = (List<String>) query.list();
			session.close();
			timer.stop();
			if (result.size() == 0)
				throw new EmptySetException();
			time = timer.getTime();
			timer.reset();
			sessionFactory.getCache().evictQueryRegions();
			log.debug("Elapsed time of HQL query: " + time);
		} catch (Exception ex) {
			ex.printStackTrace();
			timer.reset();
			return 0;
		}

		return time;
	}

}
