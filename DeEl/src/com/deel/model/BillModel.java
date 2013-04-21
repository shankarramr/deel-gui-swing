package com.deel.model;

import com.deel.domain.Bill;
import com.deel.log.Log;
import org.hibernate.Session;

/**
 *
 * @author neo
 */

public class BillModel {
    public static int fetchBillNumber() {
        int billNumber = 0;
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();        
        try {
            Bill b = (Bill) session.createQuery("from Bill b order by billNumber desc").setMaxResults(1).uniqueResult();
            billNumber = b.getBillNumber();
        } catch(Exception ex) { Log.e(ex.getClass().toString(), ex.getMessage()); }
        session.close();
        return billNumber;
    }
    
    public static void updateBillNumber(int billNumber) {
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Bill b = new Bill();
        b.setBillNumber(billNumber);
        session.save(b);
        session.close();
    }
}