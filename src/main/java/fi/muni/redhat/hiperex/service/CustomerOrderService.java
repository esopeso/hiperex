package fi.muni.redhat.hiperex.service;

// Generated Dec 20, 2012 4:59:18 PM by Hibernate Tools 4.0.0

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import fi.muni.redhat.hiperex.model.CustomerOrder;

/**
 * Home object for domain model class CustomerOrder.
 * @see fi.muni.redhat.hiperex.model.CustomerOrder
 * @author Hibernate Tools
 */
public class CustomerOrderService {

	private static final Log log = LogFactory.getLog(CustomerOrderService.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(CustomerOrder transientInstance) {
		log.debug("persisting CustomerOrder instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(CustomerOrder instance) {
		log.debug("attaching dirty CustomerOrder instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CustomerOrder instance) {
		log.debug("attaching clean CustomerOrder instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(CustomerOrder persistentInstance) {
		log.debug("deleting CustomerOrder instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CustomerOrder merge(CustomerOrder detachedInstance) {
		log.debug("merging CustomerOrder instance");
		try {
			CustomerOrder result = (CustomerOrder) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public CustomerOrder findById(int id) {
		log.debug("getting CustomerOrder instance with id: " + id);
		try {
			CustomerOrder instance = (CustomerOrder) sessionFactory.getCurrentSession().get(
					"fi.muni.redhat.hiperex.service.CustomerOrder", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CustomerOrder instance) {
		log.debug("finding CustomerOrder instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("fi.muni.redhat.hiperex.service.CustomerOrder").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
