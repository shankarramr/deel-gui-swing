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
@Table(name="tax")
public class Tax {
    private int id;
    private String type;
    private float value;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="type")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Column(name="value")
    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    public Tax(String type, float value) {
        this.type = type;
        this.value = value;
    }
    public Tax(){}
}