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

public class PrintTest4 {
    // 1 mm = 0.0393701 inch
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
        new PrintTest4().print1();
    }
}

class MyPrintable1 implements Printable {
    
    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
         if (pageIndex != 0){
              return NO_SUCH_PAGE;
         }       
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Serif", Font.PLAIN, 10));
        g2.setPaint(Color.BLACK);
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        int y = 10;
        String SPACE = "          ";
        /*for(int i = 1; i <= 50; i++){
            String str1 = "Test " + i;
            String str2 = "Test.Cont. " + i;
            String str3 = "Test Cont.Cont. " + i;
            g2.drawString(str1 + SPACE + str2 + SPACE + str3  , 0, y);
            y += 10;
        }*/
        g2.drawString("A", 0, y);
        g2.drawString("B", 100, y);
        g2.drawString("C", 195, y);
        g2.drawString(".", 0, y + 70);
        
        return PAGE_EXISTS;
    }
}