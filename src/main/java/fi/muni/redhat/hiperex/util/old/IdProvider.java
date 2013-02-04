package fi.muni.redhat.hiperex.util.old;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import fi.muni.redhat.hiperex.exception.EmptyIdPoolException;
import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;

public class IdProvider extends HibernateQuery {

	private static Logger log = Logger.getLogger(IdProvider.class);
	private List<Integer> idList;

	@SuppressWarnings("rawtypes")
	public IdProvider(SessionFactoryProvider factoryProvider, Class clazz) {
		super(factoryProvider);
		this.idList = readId(clazz);
		log.info("ID pool ready. Source: "+clazz.getName());
	}

	public IdProvider(SessionFactoryProvider factoryProvider, String hqlQuery) {
		super(factoryProvider);
		this.idList = readId(hqlQuery);
		log.info("ID pool ready. Source: "+hqlQuery);
	}

	@SuppressWarnings(value = { "unchecked", "rawtypes" })
	public List<Integer> readId(Class clazz) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> list = new LinkedList<Integer>();

		session.beginTransaction();
		Criteria criteria = session.createCriteria(clazz);
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.property("id"));
		criteria.setProjection(proList);
		list = criteria.list();
		session.close();

		return list;
	}

	@SuppressWarnings(value = { "unchecked" })
	public List<Integer> readId(String hqlQuery) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> list = new LinkedList<Integer>();

		session.beginTransaction();
		Query query = session.createQuery(hqlQuery);
		list = query.list();
		session.close();

		return list;
	}

	public SessionFactory getSessionFactory(SessionFactoryProvider factoryProvider) {
		try {
			return factoryProvider.getSessionFactory();
		} catch (Exception e) {
			// log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public int getRandomId() throws EmptyIdPoolException {
		Random randomizer = new Random();
		int listSize = idList.size();
		if (listSize == 0) throw new EmptyIdPoolException();
		return idList.get(randomizer.nextInt(listSize));
	}
	
	public void removeId(int id) {
		idList.remove(idList.indexOf(id));
	}

	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}

}
