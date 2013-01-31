package com.deel.model;

import com.deel.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author neo
 */
public class LoginModel {
    public boolean authenticate(String username,String password){
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String queryString="from User u where u.username= :username and u.password= :password";
        Query query=session.createQuery(queryString);
        query.setParameter("username", username);
        query.setParameter("password", password);
        User user = (User) query.uniqueResult();
        session.close();
        if(user != null){
            return true;
        }
        return true;//false
    }
}