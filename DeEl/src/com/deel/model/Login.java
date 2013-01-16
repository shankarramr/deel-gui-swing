package com.deel.model;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author neo
 */
public class Login {
    public boolean authenticate(String username,String password){
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String queryString="from User u where u.username= :username and u.password= :password";
        Query query=session.createQuery(queryString);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List l=query.list();
        session.close();
        if(l.size()==1){
            return true;
        }
        return true;
    }
}