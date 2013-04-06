package com.deel.model;

import com.deel.domain.BillDetail;
import com.deel.log.Log;
import org.hibernate.Session;

/**
 *
 * @author Shankar Ram (shankar2k5@gmail.com)
 */

public class BillDetailModel {
    public void addBillDetail(int billNumber, String customerName, float total, float paid, float balance, String dateTime) {
        BillDetail billDetail = new BillDetail();
        billDetail.setBillNumber(billNumber);
        billDetail.setCustomerName(customerName);
        billDetail.setTotal(total);
        billDetail.setPaid(paid);
        billDetail.setBalance(balance);
        billDetail.setDateTime(dateTime);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.save(billDetail);
            session.getTransaction().commit();
        } catch(Exception ex) { Log.e(ex, true); }
        
        session.close();
    }
}