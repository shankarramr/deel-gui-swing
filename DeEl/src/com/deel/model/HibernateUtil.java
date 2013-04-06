package com.deel.model;

import com.deel.log.Log;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * @author Shankar Ram (shankar2k5@gmail.com)
 */

public class HibernateUtil {
    private static SessionFactory sessionFactory=buildSessionFactory();
    private static ServiceRegistry serviceRegistry;
    private static SessionFactory buildSessionFactory(){
        try {
            Configuration configuration=new Configuration();
            configuration.configure();
            serviceRegistry=new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            return new Configuration().configure().buildSessionFactory(serviceRegistry);
        } catch (Exception ex) {
            Log.e(ex, true);
            throw new ExceptionInInitializerError(ex);
        }
    }

    protected static SessionFactory getSessionFactory(){return sessionFactory;}
}