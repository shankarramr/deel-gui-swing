package com.deel.model;

import com.deel.domain.Tax;
import org.hibernate.Session;

/**
 *
 * @author neo
 */
public class TaxModel {
    public String fetchTax(String type){
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Tax t=(Tax)session.createQuery("from Tax t where type= :type").setParameter("type", type).uniqueResult();
        return Float.toString(t.getValue());
    }
}