package com.deel.test;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 *
 * @author neo
 */
public class PrintTest implements  java.awt.print.Printable{

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        return 0;
    }
    public void printTest(){
    try{
        PrinterJob pj=PrinterJob.getPrinterJob();
        pj.setPrintable(this);
        pj.printDialog();
        pj.print();
    }catch(Exception ex){ex.printStackTrace();}
}
    public static void main(String[]args){
        new PrintTest().printTest();
    }
}