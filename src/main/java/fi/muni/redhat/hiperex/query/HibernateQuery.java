package fi.muni.redhat.hiperex.query;


import org.hibernate.SessionFactory;

import fi.muni.redhat.hiperex.service.SessionFactoryProvider;

public abstract class HibernateQuery {
	
	protected SessionFactory sessionFactory;
	
	public HibernateQuery(SessionFactoryProvider factoryProvider) {
		this.sessionFactory = getSessionFactory(factoryProvider);
	}

	public SessionFactory getSessionFactory(SessionFactoryProvider factoryProvider) {
		try {
			return factoryProvider.getSessionFactory();
		} catch (Exception e) {
			//log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}


}
