package fi.muni.redhat.hiperex.service;

// Generated Dec 20, 2012 4:59:18 PM by Hibernate Tools 4.0.0

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;


import fi.muni.redhat.hiperex.model.Customer;
import fi.muni.redhat.hiperex.query.HibernateQuery;

/**
 * Home object for domain model class Customer.
 * @see fi.muni.redhat.hiperex.model.Customer
 * @author Hibernate Tools
 */
public class CustomerService extends HibernateQuery{

	public CustomerService(SessionFactoryProvider factoryProvider) {
		super(factoryProvider);
	}

	public void persist(Customer transientCustomer) {
		//log.debug("persisting Customer instance");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			session.save(transientCustomer);
			session.getTransaction().commit();
			//log.debug("persist successful");
		} catch (RuntimeException re) {
			//log.error("persist failed", re);
			throw re;
		}
	}

	public void saveOrUpdate(Customer customer) {
		//log.debug("attaching dirty Customer instance");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(customer);
			session.getTransaction().commit();
			//log.debug("attach successful");
		} catch (RuntimeException re) {
			//log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Customer instance) {
		//log.debug("attaching clean Customer instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			//log.debug("attach successful");
		} catch (RuntimeException re) {
			//log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Customer persistentInstance) {
		//log.debug("deleting Customer instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			//log.debug("delete successful");
		} catch (RuntimeException re) {
			//log.error("delete failed", re);
			throw re;
		}
	}

	public Customer merge(Customer detachedInstance) {
		//log.debug("merging Customer instance");
		try {
			Customer result = (Customer) sessionFactory.getCurrentSession().merge(detachedInstance);
			//log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			//log.error("merge failed", re);
			throw re;
		}
	}

	public Customer findById(int id) {
		//log.debug("getting Customer instance with id: " + id);
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Customer instance = (Customer) session.get(Customer.class, id);
			if (instance == null) {
				//log.debug("get successful, no instance found");
			} else {
				//log.debug("get successful, instance found");
			}
			session.close();
			return instance;
		} catch (RuntimeException re) {
			//log.error("get failed", re);
			throw re;
		}
	}
	
	public Customer findByIdCQ(int id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Criteria crit = session.createCriteria(Customer.class);
			crit.add(Restrictions.eq("customerId", id));
			Customer result = (Customer)crit.uniqueResult();
			session.close();
			return result;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}			
	}
	
	public Customer findByIdHQL(int id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery("FROM Customer WHERE customerId= :id");
			query.setParameter("id", id);
			Customer customer =(Customer) query.uniqueResult();
			session.close();
			return customer;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List findByExample(Customer instance) {
		//log.debug("finding Customer instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("fi.muni.redhat.hiperex.service.Customer")
					.add(Example.create(instance)).list();
			//log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			//log.error("find by example failed", re);
			throw re;
		}
	}
}
