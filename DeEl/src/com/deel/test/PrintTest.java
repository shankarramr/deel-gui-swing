package com.deel.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;


public class PrintTest {
    public void print1(){
        
        Paper p = new Paper();        
        p.setImageableArea(0, 0, 0 , 0);
        
        PageFormat pf = new PageFormat();
        pf.setPaper(p);
        
        PrinterJob pj = PrinterJob.getPrinterJob();
        
        try {               
            MyPrintable1 mp = new MyPrintable1();
            pj.setPrintable(mp, pf);
            pj.print();                
            System.out.println("DONE");
        } catch (Exception ex) {ex.printStackTrace();}
    }
    
    public static void main(String[] args) {
        new PrintTest().print1();
    }
}
class MyPrintable1 implements Printable {
    
    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
         if (pageIndex != 0){
              return NO_SUCH_PAGE;
         }       
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Lucida", Font.PLAIN, 9));
        g2.setPaint(Color.BLACK);
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        int y = 10;
        g2.drawString("0", 0, y);
        g2.drawString("25", 25, y);
        g2.drawString("50", 50, y);
        g2.drawString("75", 75, y);
        g2.drawString("100", 100, y);
        g2.drawString("125", 125, y);
        g2.drawString("150", 150, y);
        g2.drawString("185", 185, y);
        g2.drawString(".", 0, y + 90);        
        return PAGE_EXISTS;
    }
}