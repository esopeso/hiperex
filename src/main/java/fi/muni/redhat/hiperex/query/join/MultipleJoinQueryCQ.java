package fi.muni.redhat.hiperex.query.join;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.model.Product;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;
import fi.muni.redhat.hiperex.timer.StopWatch;
import fi.muni.redhat.hiperex.util.IdProvider;
import fi.muni.redhat.hiperex.util.Repeatable;

public class MultipleJoinQueryCQ extends HibernateQuery implements Repeatable {

	private static Logger log = Logger.getLogger(MultipleJoinQueryCQ.class);
	private IdProvider idProvider;
	StopWatch timer = new StopWatch();

	public MultipleJoinQueryCQ(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
		this.idProvider = new IdProvider(factoryProvider, Customer.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public long repeat() {
		long time = 0;

		try {
			timer.start();
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Product.class, "product");
			criteria.createAlias("product.itemLists", "list");
			criteria.createAlias("list.customerOrder", "customerOrder");
			criteria.createAlias("customerOrder.customer", "customer");
			criteria.add(Restrictions.eq("customer.customerId", idProvider.getRandomId()));

			List products = criteria.list();
			session.close();
			Set<Product> prod = new HashSet<Product>();
			prod.addAll(products);
			timer.stop();
		} catch (Exception e) {
			e.printStackTrace();
			timer.reset();
			return 0;
		}
		
		time = timer.getTime();
		timer.reset();
		log.debug("Elapsed time of HC query: " + time);
		return time;
	}

}
