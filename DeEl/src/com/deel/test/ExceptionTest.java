package com.deel.test;

/**
 *
 * @author Shankar Ram (shankar2k5@gmail.com)
 */
public class ExceptionTest {
    public static void main(String[] args) {
        try {
            System.out.println(5/0);
        } catch(Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            System.out.println(ex.getClass());
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());//
            System.out.println(ex.getCause());
        }
    }
}