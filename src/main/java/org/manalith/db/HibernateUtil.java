package org.manalith.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

	private static Logger log = LoggerFactory.getLogger(HibernateUtil.class);

	private static final SessionFactory sessionFactory;

	static {
		try {
			ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
			Metadata metaData = new MetadataSources(registry)
					.getMetadataBuilder()
					.applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
					.build();
			sessionFactory = metaData.getSessionFactoryBuilder().build();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			log.error("Initial SessionFactory creation failed.", ex);
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static final ThreadLocal<Session> session = new ThreadLocal<>();

	public static Session currentSession() {
		Session s = session.get();
		// Open a new Session, if this Thread has none yet
		if (s == null) {
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}

	public static void closeSession() {
		Session s = session.get();
		if (s != null)
			s.close();
		session.set(null);
	}
}
