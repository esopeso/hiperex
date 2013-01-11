package fi.muni.redhat.hiperex.service;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class SessionFactoryProvider {
	
	protected SessionFactory sessionFactory;
	
	public SessionFactoryProvider(String resource) {
		this.sessionFactory = createSessionFactory(resource);
	}

	private SessionFactory createSessionFactory(String resource) throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.configure(resource);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		return configuration.buildSessionFactory(serviceRegistry);
		//sessionFactory.getStatistics().setStatisticsEnabled(true);
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory factory) {
		this.sessionFactory = factory;
	}
}
