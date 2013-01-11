package fi.muni.redhat.hiperex.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;

import fi.muni.redhat.hiperex.query.HibernateQuery;
import fi.muni.redhat.hiperex.service.SessionFactoryProvider;

public class IdProvider extends HibernateQuery {
	
	public IdProvider(SessionFactoryProvider factoryProvider, String hqlQuery) {
		super(factoryProvider);
		this.idList = readId(hqlQuery);
	}

	private List<Integer> idList;
	
	@SuppressWarnings(value = {"unchecked" })
	public List<Integer> readId(String hqlQuery) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> list= new LinkedList<Integer>();	
		
		session.beginTransaction();
		Query query = session.createQuery(hqlQuery);		
		list = query.list();
		session.close();
		
		return list;		
	}
	
	public int getRandomId() {
		Random randomizer = new Random();
		int listSize = idList.size();
		return idList.get(randomizer.nextInt(listSize));		
	}

	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}

}
