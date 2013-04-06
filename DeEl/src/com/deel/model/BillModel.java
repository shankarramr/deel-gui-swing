package com.deel.model;

import com.deel.domain.Bill;
import com.deel.log.Log;
import org.hibernate.Session;

/**
 *
 * @author Shankar Ram (shankar2k5@gmail.com)
 */

public class BillModel {
    public static int fetchBillNumber() {
        int billNumber = 0;
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();        
        try {
            Bill b = (Bill) session.createQuery("from Bill b order by billNumber desc").setMaxResults(1).uniqueResult();
            if(b !=null) {
                billNumber = b.getBillNumber();
            }
        } catch(Exception ex) { Log.e(ex, true); }
        session.close();
        return billNumber;
    }
    
    public void addBillNumber(int billNumber) {        
        Bill b = new Bill();
        b.setBillNumber(billNumber);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.save(b);
            session.getTransaction().commit();            
        } catch(Exception ex) { Log.e(ex, true); }        
        session.close();
    }
}