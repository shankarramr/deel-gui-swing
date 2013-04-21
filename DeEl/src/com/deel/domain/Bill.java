package com.deel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author neo
 */

@Entity
@Table(name="bill")
public class Bill {
    private int id;
    private int billNumber;

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

    public Bill(int id, int billNumber) {
        this.id = id;
        this.billNumber = billNumber;
    }
    public Bill() {}
}