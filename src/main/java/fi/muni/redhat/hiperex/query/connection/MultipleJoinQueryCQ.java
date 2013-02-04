package fi.muni.redhat.hiperex.query.connection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
	public HashMap<String, Long> execute() {
		Session session = null;
		Transaction tx = null;
		HashMap<String, Long> lapList = new HashMap<String, Long>();
		int id = -1;

		try {
			id = idProvider.getRandomId();
			timer.reset();
			timer.start();
			session = sessionFactory.getCurrentSession();
			timer.lap("connection");
			tx = session.getTransaction();
			tx.begin();

			Criteria criteria = session.createCriteria(Product.class, "product");
			criteria.createAlias("product.itemLists", "list");
			criteria.createAlias("list.customerOrder", "customerOrder");
			criteria.createAlias("customerOrder.customer", "customer");
			criteria.add(Restrictions.eq("customer.customerId", id));
			timer.lap("query");
			
			List products = criteria.list();
			tx.commit();
			Set<Product> prod = new HashSet<Product>();
			prod.addAll(products);
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
			timer.reset();
		}
		if (lapList.get("overall") == 0) log.info("Execution time = 0. Pls investigate");
		log.debug("Elapsed time of HC query: " + lapList);
		return lapList;
	}

}
