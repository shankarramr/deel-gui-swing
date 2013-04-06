package com.deel.test;

/**
 *
 * @author Shankar Ram (shankar2k5@gmail.com)
 */
public class ExceptionTest {
    public static void main(String[] args) {
        new ExceptionTest().m1();
    }
    
    private void m1() {
        m2();
    }
    
    private void m2() {        
        try {
            System.out.println(5/0);
        } catch(Exception ex) {
            System.out.println("ex.getClass(): " + ex.getClass());
            System.out.println("ex.getMessage(): " + ex.getMessage());
            System.out.println("ex.getStackTrace(): " + ex.getStackTrace()[0]);
        }
    }
}