package com.deel.printing;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author neo
 */
public class PrintBill implements Printable, BillDefaults{
    private ArrayList<String> billContent;
    private boolean isAlignCenter;
    private boolean isAlignRight;
    private static int paperWidth;
    private static String fontName = "Lucida";
    private static int fontSize = 10;
    private static int x;
    private static int y;
    private static Graphics2D g2;
            
    private void prepareBillContentHeader() {
        billContent.add(MODE_ALIGN_CENTER_OPEN);
        
        
        billContent.add(MODE_FONT_BIG_OPEN);
        billContent.add(MODE_BOLD_OPEN);
        billContent.add(COMPANY_NAME);
        billContent.add(MODE_BOLD_CLOSE);
        billContent.add(MODE_FONT_BIG_CLOSE);
        
        billContent.add(MODE_BREAK);
        
        billContent.add(ADDRESS1);
        
        billContent.add(MODE_BREAK);
        
        billContent.add(ADDRESS2);        
        
        billContent.add(MODE_BREAK);
        
        billContent.add(ADDRESS3);
        
        billContent.add(MODE_BREAK);
        
        billContent.add(PHONE_NUMBER);
        
        billContent.add(MODE_BREAK);
        
        billContent.add(MOBILE_NUMBER);
        
        billContent.add(MODE_BREAK);
        
        billContent.add(TIN_NUMBER);
        
        billContent.add(MODE_BREAK);
        
        billContent.add(ABOUT1);
        
        billContent.add(MODE_BREAK);
        
        billContent.add(ABOUT2);
        
        billContent.add(MODE_BREAK);
        
        billContent.add(CASH_BILL);
        
        billContent.add(MODE_ALIGN_CENTER_CLOSE);
        
        billContent.add(MODE_BREAK);
    }
    
    private void prepareBillContentBody() {
        billContent.add(BILL_NUMBER);
        billContent.add("0001");
                
        billContent.add(MODE_ALIGN_RIGHT_OPEN);
        billContent.add(DATE_TIME);
        billContent.add(getDateTime());
        billContent.add(MODE_ALIGN_RIGHT_CLOSE);
        
        billContent.add(MODE_BREAK);
        
        billContent.add(CUSTOMER_NAME);
    }        
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex != 0){
            return java.awt.print.Printable.NO_SUCH_PAGE;
        }               
            
        g2 = (Graphics2D) graphics;
        g2.setFont(new Font(fontName, Font.PLAIN, fontSize));
        
        y = g2.getFont().getSize();
        for(String data: billContent) {
            if(data.equals(MODE_BREAK)) {
                onModeBreak();
            } else if(data.equals(MODE_ALIGN_CENTER_OPEN)) {
                isAlignCenter = true;
            } else if (data.equals(MODE_ALIGN_CENTER_CLOSE)) {
                isAlignCenter = false;
            } else if(data.equals(MODE_ALIGN_RIGHT_OPEN)) {
                isAlignRight = true;
            } else if(data.equals(MODE_ALIGN_RIGHT_CLOSE)) {
                isAlignRight = false;
            } else if(data.equals(MODE_FONT_BIG_OPEN)) {
                onModeFontBig(true);
            } else if(data.equals(MODE_FONT_BIG_CLOSE)) {
                onModeFontBig(false);                
            } else if(data.equals(MODE_BOLD_OPEN)) {
                onModeBold(true);
            }  else if(data.equals(MODE_BOLD_CLOSE)) {
                onModeBold(false);
            } else {
                if(isAlignCenter) {
                    onModeAlignCenter(data, true);
                } else if(!isAlignCenter) {
                    onModeAlignCenter(data, false);
                } else if(isAlignRight) {
                    onModeAlignRight(data, true);                    
                } else if(!isAlignRight) {
                    onModeAlignRight(data, false);
                }
                g2.drawString(data, x, y);
                updateX(data);
            }
        }
        g2.drawString(END_OF_BILL, 0, y + 100);
        return java.awt.print.Printable.PAGE_EXISTS;
    }
    
    private void updateX(String data) {
        int dataLength = data.length();
        x += dataLength;
        x += 50;
    }
    
    private void onModeBreak() {
        y += g2.getFont().getSize();
    }
    
    private void onModeAlignCenter(String data, boolean active) {
        if(active) {
            int dataLength = data.length();
            x = (paperWidth / 2) - (dataLength - (dataLength / 2)) - 30;
        } else {
            x = 0;
        }
    }
    
    private void onModeAlignRight(String data, boolean active) {
        if(active) {
            int dataLength = data.length();
            x = paperWidth - dataLength;
        } else {
            x = 0;
        }
    }
    
    private void onModeFontBig(boolean active) {
        if(active) {
            g2.setFont(new Font(g2.getFont().getName(), g2.getFont().getStyle(), fontSize + 2));
        } else {
            g2.setFont(new Font(g2.getFont().getName(), g2.getFont().getStyle(), fontSize));            
        }
    }
    
    private void onModeBold(boolean active) {
        if(active) {
            g2.setFont(new Font(g2.getFont().getName(), Font.BOLD, g2.getFont().getSize()));
        } else {
            g2.setFont(new Font(g2.getFont().getName(), Font.PLAIN, g2.getFont().getSize()));
        }
    }
    
    private void prepareBillContent() {       
        prepareBillContentHeader();
        prepareBillContentBody();
    }
    
    private String getDateTime() {
        //Date
        String date = Integer.toString(Calendar.getInstance().get(Calendar.DATE));
        if(date.length() < 2) {
            date = "0" + date;
        }
        String month = Integer.toString(Calendar.getInstance().get(Calendar.MONTH));
        if(month.length() < 2) {
            month = "0" + month;
        }
        String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));        
        String fullDate = date + "-" + month + "-" + year;
        
        //TIME
        String hour = Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        if(hour.length() < 2) {
            hour = "0" + hour;
        }
        String minute = Integer.toString(Calendar.getInstance().get(Calendar.MINUTE));
        if(minute.length() < 2) {
            minute = "0" + minute;
        }
        String second = Integer.toString(Calendar.getInstance().get(Calendar.SECOND));
        if(second.length() < 2) {
            second = "0" + second;
        }
        String fullTime = hour + ":" + minute + ":" + second;
        String fullDateTime = fullDate + " " + fullTime;
        return fullDateTime;
    }
    
    public void doPrint() {
        prepareBillContent();        
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(this, prepareBillPaper());
        try {
            printerJob.print();
        } catch(Exception ex) {ex.printStackTrace();}
    }    
    
    private PageFormat prepareBillPaper() {
        paperWidth = 195; //This is from own printed measurement with font size 10 and not actual width of paper.
        Paper paper = new Paper();
        paper.setImageableArea(0, 0, 0 , 0);
                 
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
        return pageFormat;
    }
    
    public PrintBill() {
        billContent = new ArrayList();
    }
    
    public static void main(String[] args) {
        new PrintBill().doPrint();
    }
}