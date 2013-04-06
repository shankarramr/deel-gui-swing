package com.deel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Shankar Ram (shankar2k5@gmail.com)
 */

@Entity
@Table(name="bill_detail")
public class BillDetail {
    private int id;
    private int billNumber;
    private String customerName;
    private float total;
    private float paid;
    private float balance;
    private String dateTime;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="bill_number")
    public int getBillNumber() {
        return billNumber;
    }
    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;    
    }

    @Column(name="customer_name")
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Column(name="total")
    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
    }

    @Column(name="paid")
    public float getPaid() {
        return paid;
    }
    public void setPaid(float paid) {
        this.paid = paid;
    }

    @Column(name="balance")
    public float getBalance() {
        return balance;
    }
    public void setBalance(float balance) {
        this.balance = balance;
    }

    @Column(name="date_time")
    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }    
}