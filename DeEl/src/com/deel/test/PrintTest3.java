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
import javax.swing.JTextArea;

public class PrintTest3 {
    // 1 mm = 0.0393701 inch
    public void print1(){
        
        /*PageFormat pf = pj.pageDialog(new PageFormat());        
        System.out.println(pf.getWidth() + " , " + pf.getHeight() + " / " + pf.getOrientation());
        Paper p = new Paper();
        double margin = 0;
        p.setImageableArea(margin, margin, 80, 50);
        pf.setPaper(p);*/
        
        Paper p = new Paper();        
        p.setImageableArea(0, 0, 0 , 0);
        
        PageFormat pf = new PageFormat();
        pf.setPaper(p);
        
        PrinterJob pj = PrinterJob.getPrinterJob();
        
        try {               
            MyPrintable mp = new MyPrintable();
            for(int i = 1; i <= 10; i++){
                mp.setMsg("Msg" + i + "          " + "Msg cont");
                pj.setPrintable(mp, pf);
                pj.print();
                System.out.println("Msg" + i);
                if(i == 10){
                    mp.setMsg("#END#");
                    pj.setPrintable(mp, pf);
                    pj.print();                    
                }
            }
            System.out.println("#END#");
        } catch (Exception ex) {ex.printStackTrace();}
    }
    
    public static void main(String[] args) {
        new PrintTest3().print1();
    }
}

class MyPrintable implements Printable {
    private String msg;
    
    protected void setMsg(String msg){
        this.msg = msg;
    }
    
    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
         if (pageIndex != 0){
              return NO_SUCH_PAGE;
         }       
        /*JTextArea jta = new JTextArea();
        jta.append("\tHEADER");
        jta.append("\n");
        jta.append("Col 1");
        jta.append("\tCol 2");
        jta.append("\tCol 3");
        jta.append("\tCol 4");
        jta.append("\tCol 5");*/
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Serif", Font.PLAIN, 10));
        g2.setPaint(Color.BLACK);
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        if(msg.equals("#END#")){
            System.out.println("if1");
            msg = "-----------";
            g2.drawString(msg, 50,50);
            System.out.println("if2");
        }else{
            g2.drawString(msg, 0,10);
        }
        
        return PAGE_EXISTS;
    }
}