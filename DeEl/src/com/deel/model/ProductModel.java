package com.deel.model;

import com.deel.domain.Product;
import com.deel.log.Log;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Shankar Ram (shankar2k5@gmail.com)
 */
public class ProductModel {
    private static List<String> productList;

    public static void updateStocks(String code, int quantity){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Product product = (Product) session.createQuery("from Product p where p.code= :code").setParameter("code", code).uniqueResult();
        int stock = product.getStock() - quantity;
        product.setStock(stock);
        try {
            session.update(product);
            session.getTransaction().commit();
        } catch(Exception ex) { Log.e(ex, true); }
        session.close();
    }

    public static Product fetchSingleProduct(String code){
        Product product = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            product = (Product) session.createQuery("from Product p where p.code= :code").setParameter("code", code).uniqueResult();
        } catch(Exception ex) { Log.e(ex, true); }
        session.close();
        return product;
    }

    public static List<String> dynamicSearch(String q){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String queryString = "from Product p where p.meta like :q";
        Query query = session.createQuery(queryString);
        q = "%"+q+"%";
        query.setParameter("q", q);
        List<Product> l = query.list();        
        productList = new ArrayList();
        if(l.size() > 0){
            for(Product product:l){
                String code = product.getCode();
                String category = product.getCategory();
                String brand = product.getBrand();
                productList.add(category +" - " + brand + ":" + code );
            }
        }
        session.close();
        return productList;
    }

    public static List<String> fetchAllProduct(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Product> l = session.createQuery("from Product").list();
        if(l.size() > 0){
            productList = new ArrayList();
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
    
    public static boolean addProduct(String code, String category, String brand, String image, String stock, String price, String meta, String description) {
        boolean result = false;
        Product p = new Product();
        p.setCode(code);
        p.setCategory(category);
        p.setBrand(brand);
        p.setImage(image);
        p.setStock(Integer.parseInt(stock));
        p.setPrice(Float.parseFloat(price));
        p.setMeta(meta);
        p.setDescription(description);
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try{
            session.save(p);
            session.getTransaction().commit();
            result = true;
        } catch(Exception ex) { Log.e(ex, true); }        
        session.close();
        return result;
    }
}