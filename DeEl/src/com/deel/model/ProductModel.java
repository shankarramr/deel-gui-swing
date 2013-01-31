package com.deel.model;

import com.deel.domain.Product;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author neo
 */
public class ProductModel {
    private List<String> productList;

    public void updateStocks(String code, int quantity){
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Product product=(Product) session.createQuery("from Product p where p.code= :code").setParameter("code", code).uniqueResult();
        int stock = product.getStock() - quantity;
        product.setStock(stock);
        session.update(product);
        session.getTransaction().commit();
    }

    public Product fetchSingleProduct(String code){
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Product product=(Product) session.createQuery("from Product p where p.code= :code").setParameter("code", code).uniqueResult();
        return product;
    }

    public List<String> dynamicSearch(String q){
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String queryString="from Product p where p.meta like :q";
        Query query=session.createQuery(queryString);
        q="%"+q+"%";
        query.setParameter("q", q);
        List<Product> l=query.list();
        if(l.size() > 0){
            productList=new ArrayList();
            for(Product product:l){
                String code=product.getCode();
                String category=product.getCategory();
                String brand=product.getBrand();
                productList.add(category +" - " + brand + ":" + code );
            }
        }
        session.close();
        return productList;
    }

    public List<String> fetchAllProduct(){
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Product> l=session.createQuery("from Product").list();
        if(l.size() > 0){
            productList=new ArrayList();
            for(Product product:l){
                String code=product.getCode();
                String category=product.getCategory();
                String brand=product.getBrand();
                productList.add(category +" - " + brand + ":" + code);
            }
        }
        session.close();
        return productList;
    }
}