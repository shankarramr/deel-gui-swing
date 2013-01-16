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
@Table(name="product")
public class Product {
    private int id;
    private String code;
    private String category;
    private String brand;
    private String image;
    private int stock;
    private float price;
    private String meta;
    private String description;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="code")
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Column(name="category")
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name="brand")
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name="image")
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    @Column(name="stock")
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    @Column(name="price")
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @Column(name="meta")
    public String getMeta() {
        return meta;
    }
    public void setMeta(String meta) {
        this.meta = meta;
    }

    @Column(name="description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Product(String code, String category, String brand, String image, int stock, float price, String meta, String description) {
        this.code = code;
        this.category = category;
        this.brand = brand;
        this.image = image;
        this.stock = stock;
        this.price = price;
        this.meta = meta;
        this.description = description;
    }
    public Product(){}
}