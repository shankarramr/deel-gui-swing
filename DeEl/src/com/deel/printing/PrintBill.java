package com.deel.printing;

import com.deel.log.Log;
import com.deel.model.BillModel;
import com.deel.model.ProductModel;
import com.deel.view.BillerForm;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Shankar Ram (shankar2k5@gmail.com)
 */

public class PrintBill implements Printable {
    private Properties props;
    private final String BILL_DEFAULTS_FILE_LOCATION = "src/bill_defaults.properties";
    private List<BillContent> billContent;
    private DefaultTableModel dtm;
    private BillContent bc;
    private int billNumber;
    private String billDetailItemCount;
    private String billDetailQuantityCount;
    private String billDetailTax;
    private String billDetailTaxAmount;
    private String billDetailSubTotal;
    private String billDetailRoundOff;
    private String billDetailTotal;
    private String billDetailPaid;
    private String billDetailBalance;
    private String billDetailCustomerName;
    private boolean isGreetingMessage;
    
    private void addBillContent(String key, int value, boolean isNewLine) {
        bc = new BillContent(key, value);
        billContent.add(bc);
        if(isNewLine) {
            bc = new BillContent(getValueAsString("SYMBOL_NEW_LINE"), getValueAsInt("FONT_SIZE"));
            billContent.add(bc);
        }
    }
    
    private String getValueAsString(String key) {
        return props.getProperty(key);
    }
    
    private int getValueAsInt(String key) {
        try {
            return Integer.parseInt(props.getProperty(key));
        } catch(Exception ex) { Log.e(ex.getClass().toString(), ex.getMessage()); }
        return 0;
    }
    
    private void prepareBillContentHeader() {
        //Company Name
        addBillContent(getValueAsString("COMPANY_NAME"), getValueAsInt("COMPANY_NAME_POSITION"), true);
        
        //Address1
        addBillContent(getValueAsString("ADDRESS1"), getValueAsInt("ADDRESS1_POSITION"), true);
        
        //Address2
        addBillContent(getValueAsString("ADDRESS2"), getValueAsInt("ADDRESS2_POSITION"), true);
        
        //Address3
        addBillContent(getValueAsString("ADDRESS3"), getValueAsInt("ADDRESS3_POSITION"), true);
        
        //Phone Number
        addBillContent(getValueAsString("PHONE_NUMBER"), getValueAsInt("PHONE_NUMBER_POSITION"), true);
        
        //Mobile Number
        addBillContent(getValueAsString("MOBILE_NUMBER"), getValueAsInt("MOBILE_NUMBER_POSITION"), true);
        
        //Tin Number
        addBillContent(getValueAsString("TIN_NUMBER"), getValueAsInt("TIN_NUMBER_POSITION"), true);
        
        //About1
        addBillContent(getValueAsString("ABOUT1"), getValueAsInt("ABOUT1_POSITION"), true);
        
        //About2
        addBillContent(getValueAsString("ABOUT2"), getValueAsInt("ABOUT2_POSITION"), true);
        
        //Cash Bill
        addBillContent(getValueAsString("CASH_BILL"), getValueAsInt("CASH_BILL_POSITION"), true);
    }
    
    private void prepareBillContentBody() {
        //Bill Number
        addBillContent(getValueAsString("BILL_NUMBER"), getValueAsInt("BILL_NUMBER_POSITION"), false);        
        addBillContent(getValueAsString("SYMBOL_COLON"), getValueAsInt("BILL_NUMBER_COLON_POSITION"), false);
        billNumber = BillModel.fetchBillNumber() + 1;
        addBillContent(String.format("%06d%n", billNumber), getValueAsInt("BILL_NUMBER_VALUE_POSITION"), false);
        
        //Date Time
        addBillContent(getValueAsString("DATE_TIME"), getValueAsInt("DATE_TIME_POSITION"), false);        
        addBillContent(getValueAsString("SYMBOL_COLON"), getValueAsInt("DATE_TIME_COLON_POSITION"), false);        
        Calendar c = Calendar.getInstance();
        addBillContent(String.format("%td/%tm/%ty %tH:%tM", c, c, c, c, c), getValueAsInt("DATE_TIME_VALUE_POSITION"), true);
        
        //Customer Name
        addBillContent(getValueAsString("CUSTOMER_NAME"), getValueAsInt("CUSTOMER_NAME_POSITION"), false);        
        addBillContent(getValueAsString("SYMBOL_COLON"), getValueAsInt("CUSTOMER_NAME_COLON_POSITION"), false);        
        addBillContent(billDetailCustomerName, getValueAsInt("CUSTOMER_NAME_VALUE_POSITION"), true);
        
        //Column Heading
        addBillContent(getValueAsString("SERIAL_NUMBER"), getValueAsInt("SERIAL_NUMBER_POSITION"), false);
        addBillContent(getValueAsString("PRODUCT"), getValueAsInt("PRODUCT_POSITION"), false);
        addBillContent(getValueAsString("PRICE"), getValueAsInt("PRICE_POSITION"), false);
        addBillContent(getValueAsString("QUANTITY"), getValueAsInt("QUANTITY_POSITION"), false);
        addBillContent(getValueAsString("AMOUNT"), getValueAsInt("AMOUNT_POSITION"), true);
        
        //Symbol Hyphen
        addBillContent(getValueAsString("SYMBOL_HYPHEN"), getValueAsInt("SYMBOL_HYPHEN_POSITION"), true);
        
        //Column Content
        for(int i = 0; i < dtm.getRowCount(); i++) {
            String tmpSerialNumber = Integer.toString(i+1);
            String tmpProduct = dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_PRODUCT).toString();
            if(tmpProduct.length() > 12) {
                tmpProduct = tmpProduct.substring(0, 12);
            }
            String tmpPrice = dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_PRICE).toString();
            String tmpQuantity = dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_QUANTITY).toString();
            String tmpAmount = dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_AMOUNT).toString();
            addBillContent(tmpSerialNumber, getValueAsInt("SERIAL_NUMBER_VALUE_POSITION"), false);
            addBillContent(tmpProduct, getValueAsInt("PRODUCT_VALUE_POSITION"), false);
            addBillContent(tmpPrice, getValueAsInt("PRICE_VALUE_POSITION"), false);
            addBillContent(tmpQuantity, getValueAsInt("QUANTITY_VALUE_POSITION"), false);
            addBillContent(tmpAmount, getValueAsInt("AMOUNT_VALUE_POSITION"), true);
        }
        
        //Symbol Hyphen
        addBillContent(getValueAsString("SYMBOL_HYPHEN"), getValueAsInt("SYMBOL_HYPHEN_POSITION"), true);
    }   
    
    private void prepareBillContentFooter() {
        //Bill Detail Item Count
        addBillContent(getValueAsString("TOTAL_ITEM"), getValueAsInt("TOTAL_ITEM_POSITION"), false);
        addBillContent(getValueAsString("SYMBOL_COLON"), getValueAsInt("TOTAL_ITEM_COLON_POSITION"), false);
        addBillContent(billDetailItemCount, getValueAsInt("TOTAL_ITEM_VALUE_POSITION"), false);
        
        //Tax
        if(!billDetailTax.trim().equals("0")) {
            String tax = getValueAsString("TAX_IF") + " @ " + billDetailTax + "%";
            addBillContent(tax, getValueAsInt("TAX_POSITION"), false);
            addBillContent(getValueAsString("SYMBOL_COLON"), getValueAsInt("TAX_COLON_POSITION"), false);
            addBillContent(billDetailTaxAmount, getValueAsInt("TAX_VALUE_POSITION"), true);
        } else {
            addBillContent(getValueAsString("TAX_ELSE"), getValueAsInt("TAX_POSITION"), true);
        }
        
        //Bill Detail Quantity Count
        addBillContent(getValueAsString("TOTAL_QUANTITY"), getValueAsInt("TOTAL_QUANTITY_POSITION"), false);
        addBillContent(getValueAsString("SYMBOL_COLON"), getValueAsInt("TOTAL_QUANTITY_COLON_POSITION"), false);
        addBillContent(billDetailQuantityCount, getValueAsInt("TOTAL_QUANTITY_VALUE_POSITION"), false);
        
        //Bill Detail Sub Total
        addBillContent(getValueAsString("SUB_TOTAL"), getValueAsInt("SUB_TOTAL_POSITION"), false);
        addBillContent(getValueAsString("SYMBOL_COLON"), getValueAsInt("SUB_TOTAL_COLON_POSITION"), false);
        addBillContent(billDetailSubTotal, getValueAsInt("SUB_TOTAL_VALUE_POSITION"), true);
        
        //Round Off
        addBillContent(getValueAsString("ROUND_OFF"), getValueAsInt("ROUND_OFF_POSITION"), false);
        addBillContent(getValueAsString("SYMBOL_COLON"), getValueAsInt("ROUND_OFF_COLON_POSITION"), false);
        addBillContent(billDetailRoundOff, getValueAsInt("ROUND_OFF_VALUE_POSITION"), true);
        
        //Bill Detail Total
        addBillContent(getValueAsString("TOTAL"), getValueAsInt("TOTAL_POSITION"), false);
        addBillContent(getValueAsString("SYMBOL_COLON"), getValueAsInt("TOTAL_COLON_POSITION"), false);
        addBillContent(billDetailTotal, getValueAsInt("TOTAL_VALUE_POSITION"), true);
        
        //Bill Detail Paid
        addBillContent(getValueAsString("PAID"), getValueAsInt("PAID_POSITION"), false);
        addBillContent(getValueAsString("SYMBOL_COLON"), getValueAsInt("PAID_COLON_POSITION"), false);
        addBillContent(billDetailPaid, getValueAsInt("PAID_VALUE_POSITION"), true);
        
        //Bill Detail Balance
        addBillContent(getValueAsString("BALANCE"), getValueAsInt("BALANCE_POSITION"), false);
        addBillContent(getValueAsString("SYMBOL_COLON"), getValueAsInt("BALANCE_COLON_POSITION"), false);
        addBillContent(billDetailBalance, getValueAsInt("BALANCE_VALUE_POSITION"), true);
        
        //Disclaimer
        addBillContent(getValueAsString("DISCLAIMER"), getValueAsInt("DISCLAIMER_POSITION"), true);
        
        //Greeting Message
        if(isGreetingMessage) {
            addBillContent(getValueAsString("GREETING_MESSAGE"), getValueAsInt("GREETING_MESSAGE_POSITION"), true);            
        }
        
        //Thank You
        addBillContent(getValueAsString("THANK_YOU"), getValueAsInt("THANK_YOU_POSITION"), true);
    }
    
    private void prepareBillContentEnd() {
        bc = new BillContent(getValueAsString("SYMBOL_END_OF_BILL"), getValueAsInt("SYMBOL_END_OF_BILL_POSITION"));
        billContent.add(bc);    
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex != 0){
            return java.awt.print.Printable.NO_SUCH_PAGE;
        }                           
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setFont(new Font(getValueAsString("FONT_NAME"), Font.PLAIN, getValueAsInt("FONT_SIZE")));        
        int y = getValueAsInt("FONT_SIZE");
        
        for (Iterator it = billContent.iterator(); it.hasNext(); ) {
            BillContent tmpBc = (BillContent) it.next();
            String key = tmpBc.getContent();
            int value = tmpBc.getPosition();
            if(key.equals(getValueAsString("SYMBOL_NEW_LINE"))) {
                y += value;
                continue;
            } else if(key.equals(getValueAsString("SYMBOL_END_OF_BILL"))) {
                y += value;
            }
            g2.drawString(key, value, y);
        }
        return java.awt.print.Printable.PAGE_EXISTS;
    }    
    
    private void prepareBillContent() {       
        prepareBillContentHeader();
        prepareBillContentBody();
        prepareBillContentFooter();
        prepareBillContentEnd();
    }    
    
    public boolean doPrint() {
        prepareBillContent();
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(this, prepareBillPaper());
        try {
            printerJob.print();
            updateStocksInProduct();
            BillModel.updateBillNumber(billNumber);
            return true;
        } catch(Exception ex) { Log.e(ex.getClass().toString(), ex.getMessage()); }
        return false;
    }    
    
    private PageFormat prepareBillPaper() {
        Paper paper = new Paper();
        paper.setImageableArea(0, 0, 0 , 0);
                 
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
        return pageFormat;
    }
    
    public PrintBill(DefaultTableModel dtm, String billDetailItemCount, String billDetailQuantityCount, String billDetailTax, String billDetailTaxAmount, String billDetailSubTotal, String billDetailRoundOff, String billDetailTotal, String billDetailPaid,String billDetailBalance,String billDetailCustomerName, boolean isGreetingMessage) {
        props = new Properties();
        try {
            props.load(new FileInputStream(new File(BILL_DEFAULTS_FILE_LOCATION)));
        } catch(Exception ex) { Log.e(ex.getClass().toString(), ex.getMessage()); }
        billContent = new ArrayList();
        this.dtm = dtm;
        this.billDetailItemCount = billDetailItemCount;
        this.billDetailQuantityCount = billDetailQuantityCount;
        this.billDetailTax = billDetailTax;
        this.billDetailTaxAmount = billDetailTaxAmount;
        this.billDetailSubTotal = billDetailSubTotal;
        this.billDetailRoundOff = billDetailRoundOff;
        this.billDetailTotal = billDetailTotal;
        this.billDetailPaid = billDetailPaid;
        this.billDetailBalance = billDetailBalance;
        this.billDetailCustomerName = billDetailCustomerName;
        this.isGreetingMessage = isGreetingMessage;
    }
    
    private void updateStocksInProduct() {
        for(int i=0; i< dtm.getRowCount(); i++) {
            String code = dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_CODE).toString();
            int quantity = Integer.parseInt(dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_QUANTITY).toString());
            try{
                ProductModel.updateStocks(code, quantity);
            } catch(Exception ex) { Log.e(ex.getClass().toString(), ex.getMessage()); }
        }
    }
     
    private class BillContent {
        private String content;
        private int position;

        public BillContent(String content, int position) {
            this.content = content;
            this.position = position;
        }
        
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }   
}