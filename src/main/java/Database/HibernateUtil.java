package Database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
private static SessionFactory factory;

static {
	try {
		Configuration conf = new Configuration();
		conf.configure("hibernate.cfg.xml");
		ServiceRegistry serviceRegistry
        = new StandardServiceRegistryBuilder()
            .applySettings(conf.getProperties()).build();
		conf.addAnnotatedClass(User.class);
		conf.addAnnotatedClass(Event.class);
		conf.addAnnotatedClass(EventUserRelation.class);
		factory = conf.buildSessionFactory(serviceRegistry);
	} catch(Exception e) {
		System.err.println("Initialiszing factory failed! " + e);
	}
}

static synchronized SessionFactory getFactory() {
	return factory;
}

}
