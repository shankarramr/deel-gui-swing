package com.deel.model;

import com.deel.domain.Tax;
import org.hibernate.Session;

/**
 *
 * @author Shankar Ram (shankar2k5@gmail.com)
 */

public class TaxModel {
    public String fetchTax(String type){
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Tax t = (Tax) session.createQuery("from Tax t where type= :type").setParameter("type", type).uniqueResult();
        session.close();
        return Float.toString(t.getValue());
    }
}