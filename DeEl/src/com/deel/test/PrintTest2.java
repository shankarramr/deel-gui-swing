package com.deel.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 *
 * @author neo
 */
public class PrintTest2 {
    
    public void print1(){
        PrinterJob pj = PrinterJob.getPrinterJob();
        final PageFormat pf = pj.defaultPage();
        System.out.println(pj.defaultPage().getHeight() + " , " + pj.defaultPage().getWidth());
        Paper p = new Paper();
        double margin = 0;
        p.setImageableArea(margin, margin, 80, 50);
        pf.setPaper(p);
        pj.setPrintable(new Printable() {

            @Override
            public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
                 if (pageIndex != 0)
                      return NO_SUCH_PAGE;
                Graphics2D g2 = (Graphics2D) g;
                g2.setFont(new Font("Serif", Font.PLAIN, 10));
                g2.setPaint(Color.black);
                g2.drawString("Hello", 5, 5);
                //Rectangle2D outline = new Rectangle2D.Double(pf.getImageableX(), pf.getImageableY(), pf.getImageableWidth(), pf.getImageableHeight());
                //g2.draw(outline);
                return PAGE_EXISTS;
            }
        },pf);
        
        if(pj.printDialog()){
            try {                
                pj.print();
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }
    
    public static void main(String[] args) {
        new PrintTest2().print1();
    }
}