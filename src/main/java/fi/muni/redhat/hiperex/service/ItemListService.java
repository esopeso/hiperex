package fi.muni.redhat.hiperex.service;

// Generated Dec 20, 2012 4:59:18 PM by Hibernate Tools 4.0.0

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import fi.muni.redhat.hiperex.model.ItemList;
import fi.muni.redhat.hiperex.model.ItemListId;

/**
 * Home object for domain model class ItemList.
 * @see fi.muni.redhat.hiperex.model.ItemList
 * @author Hibernate Tools
 */
public class ItemListService {

	private static final Log log = LogFactory.getLog(ItemListService.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(ItemList transientInstance) {
		log.debug("persisting ItemList instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ItemList instance) {
		log.debug("attaching dirty ItemList instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ItemList instance) {
		log.debug("attaching clean ItemList instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ItemList persistentInstance) {
		log.debug("deleting ItemList instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ItemList merge(ItemList detachedInstance) {
		log.debug("merging ItemList instance");
		try {
			ItemList result = (ItemList) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ItemList findById(ItemListId id) {
		log.debug("getting ItemList instance with id: " + id);
		try {
			ItemList instance = (ItemList) sessionFactory.getCurrentSession().get(
					"fi.muni.redhat.hiperex.service.ItemList", id);
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

	public List findByExample(ItemList instance) {
		log.debug("finding ItemList instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("fi.muni.redhat.hiperex.service.ItemList")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
