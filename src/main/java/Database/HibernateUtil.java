package Database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
/**
 * This class configures the SessionFactory of hibernate. 
 * A SessionFactory manages creation, opening, closing and deletion of sessions.
 * A SessionFactory is considered as heavy-weight. Therefore there should only exist one instance. 
 */
public class HibernateUtil {
private static SessionFactory factory;
/**
 * Configure and build a SessionFactory
 */
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

/**
 * @return factory SessionFactory
 */
public static synchronized SessionFactory getFactory() {
	return factory;
}

}
