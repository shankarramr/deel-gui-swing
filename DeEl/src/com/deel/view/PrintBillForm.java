package com.deel.view;

import com.deel.model.ProductModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author neo
 */
public class PrintBillForm extends javax.swing.JFrame {
    private DefaultTableModel dtm;
    private String billDetailItemCount;
    private String billDetailQuantityCount;
    private String billDetailTax;
    private String billDetailTaxAmount;
    private String billDetailSubTotal;
    private String billDetailPaid;
    private String billDetailBalance;
    private String billDetailCustomerName;
    private boolean isBillReady;
    private static final String NEW_LINE = "\n";
    protected static final String FONT_BOLD_OPEN = "<b>";
    protected static final String FONT_BOLD_CLOSE = "</b>";
    protected static final String FONT_ITALIC_OPEN = "<i>";
    protected static final String FONT_ITALIC_CLOSE = "</i>";
    private static final String TAB_SPACE = "      ";
    private static final String TAB_SPACE_FIVE = TAB_SPACE + TAB_SPACE + TAB_SPACE + TAB_SPACE + TAB_SPACE;
    private static final String TAB_SPACE_FOUR = TAB_SPACE + TAB_SPACE + TAB_SPACE;
    private static final String TAB_SPACE_SEVEN = TAB_SPACE + TAB_SPACE + TAB_SPACE + TAB_SPACE + TAB_SPACE + TAB_SPACE + TAB_SPACE;
    private static final String COMPANY_NAME   = "           " + "DEEBAAS & CO.,";
    private static final String ADDRESS1       = "                   " + "61, Annai Towers,";
    private static final String ADDRESS2       = "        " + "Annai Velankanni Church Road,";
    private static final String ADDRESS3       = "        " + "Besant Nagar, Chennai 600090";
    private static final String PHONE_NUMBER   = "   " + "Ring: 044-24915144, 044-24900062";
    private static final String MOBILE_NUMBER  = "    " + "Mob: 9444894323, 9444953244";
    private static final String TIN_NUMBER     = "                  " + "Tin: 33630863627";
    private static final String ABOUT1         = " " + "Electrical Products, Sales, Service,";
    private static final String ABOUT2         = "         " + "Plumbing, Borewell & Re-winding";
    private static final String CASH_BILL      = "CASH BILL";
    private static final String BILL_NUMBER    = "Bill No: ";
    private static final String DATE_TIME      = "Date: ";
    private static final String CUSTOMER_NAME  = "Cust. Name: ";
    private static final String SERIAL_NUMBER  = "S.No.";
    private static final String PRODUCT        = "Product";
    private static final String PRICE          = "Price";
    private static final String QUANTITY       = "Qty";
    private static final String AMOUNT         = "Amt";
    private static final String TOTAL_ITEM     = "Total Item(s): ";
    private static final String TOTAL_QUANTITY = "Total Qty(s): ";
    private static final String TAX_IF         = "VAT";
    private static final String TAX_ELSE       = "(Inclusive of VAT)";
    private static final String SUB_TOTAL      = "Sub Total: ";
    private static final String ROUND_OFF      = "Round Off: ";
    private static final String TOTAL          = "Total: ";
    private static final String PAID           = "Paid: ";
    private static final String BALANCE        = "Bal.: ";
    private static final String DISCLAIMER     = "   Goods once sold cannot be returned/exchanged";
    private static final String COME_AGAIN     = "            ## Thank you! Please visit again ##";
    private static final String SYMBOL_HYPHEN1 = "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - "; //80
    private ArrayList<String> billContent;

    private void doPrint() {
        if(isBillReady) {            
            //setting up Paper
            Paper paper = new Paper();        
            paper.setImageableArea(0, 0, 0 , 0);

            //setting up PageFormat
            PageFormat pageFormat = new PageFormat();
            pageFormat.setPaper(paper);

            //setting up PrinterJob
            PrinterJob printerJob = PrinterJob.getPrinterJob();
                
            try {
                PrintBill printBill = new PrintBill(billContent);
                printerJob.setPrintable(printBill, pageFormat);
                printerJob.print();
                updateStocks();
            } catch(Exception ex) {ex.printStackTrace();}
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Bill is empty", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void prepareBill(){
        billContent = new ArrayList();
        Font f = new Font("LucidaConsole", Font.PLAIN, 10);
        jTextAreaBill.setFont(f);
        
        //Company Name
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.FONT_BOLD_OPEN);
        billContent.add(PrintBillForm.COMPANY_NAME);
        billContent.add(PrintBillForm.FONT_BOLD_CLOSE);
        
        //Address1
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.ADDRESS1);
        
        //Address2
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.ADDRESS2);
        
        //Address3
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.ADDRESS3);
        
        //Phone Number
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.PHONE_NUMBER);
        
        //Mobile Number
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.MOBILE_NUMBER);
        
        //TIN Number
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.TIN_NUMBER);
        
        //About1
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.ABOUT1);
        
        //About2
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.ABOUT2);
        
        //Cash Bill
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE_FIVE);
        billContent.add(PrintBillForm.FONT_ITALIC_OPEN);
        billContent.add(PrintBillForm.CASH_BILL);
        billContent.add(PrintBillForm.FONT_ITALIC_CLOSE);
        
        //Bill Number
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.BILL_NUMBER);
        billContent.add("000000001");
        
        //Date
        billContent.add(PrintBillForm.TAB_SPACE + PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.DATE_TIME);
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
        billContent.add(fullDate);
        billContent.add(" ");
        billContent.add(fullTime);
        
        //Customer Name
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.CUSTOMER_NAME);
        billContent.add(billDetailCustomerName);
        
        //Column Heading
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.SERIAL_NUMBER);
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.PRODUCT);
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.PRICE);
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.QUANTITY);
        billContent.add(PrintBillForm.TAB_SPACE);
        billContent.add(PrintBillForm.AMOUNT);
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.SYMBOL_HYPHEN1); 
        billContent.add(PrintBillForm.NEW_LINE);                        
        
        //Column Content
        for(int i = 0; i < dtm.getRowCount(); i++){
            String serialNumber = Integer.toString(i+1);
            String product = dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_PRODUCT).toString();
            String price = dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_PRICE).toString();
            String quantity = dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_QUANTITY).toString();
            String amount = dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_AMOUNT).toString();
            billContent.add(serialNumber);
            billContent.add(PrintBillForm.TAB_SPACE);
            billContent.add(product);
            billContent.add(PrintBillForm.TAB_SPACE);
            billContent.add(price);
            billContent.add(PrintBillForm.TAB_SPACE);
            billContent.add(quantity);
            billContent.add(PrintBillForm.TAB_SPACE);
            billContent.add(amount);
            billContent.add(PrintBillForm.TAB_SPACE);
            billContent.add(PrintBillForm.NEW_LINE);
        }
        billContent.add(PrintBillForm.SYMBOL_HYPHEN1);
        
        //Bill Detail Item Count
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TOTAL_ITEM);
        billContent.add(billDetailItemCount);
        
        //Bill Detail Tax        
        billContent.add(PrintBillForm.TAB_SPACE_FOUR);
        if(!billDetailTax.trim().equals("0")) {
            String tmp = PrintBillForm.TAX_IF + " @ " + billDetailTax + "%" + ": ";
            billContent.add(tmp);
            billContent.add(billDetailTaxAmount);
        } else {
            billContent.add(PrintBillForm.TAX_ELSE);
        }
        
        //Bill Detail Quantity Count
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TOTAL_QUANTITY);
        billContent.add(billDetailQuantityCount);
                
        //Bill Detail Sub Total
        billContent.add(PrintBillForm.TAB_SPACE_FOUR);
        billContent.add(PrintBillForm.SUB_TOTAL);        
        billContent.add(billDetailSubTotal);
        float subTotal = Float.parseFloat(billDetailSubTotal);
        String tmpSubTotal = billDetailSubTotal.replace(".", ":");
        int decimal = Integer.parseInt(tmpSubTotal.split(":")[1]);
        float total = 0.0f;
        if(decimal <= 49) {
            total = (float) Math.floor(Float.parseFloat(billDetailSubTotal));
        } else {
            total = (float) Math.ceil(Float.parseFloat(billDetailSubTotal));
        }
        
        //Round Off
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE_SEVEN);
        billContent.add(PrintBillForm.ROUND_OFF);
        float roundOff = total - subTotal;
        billContent.add(String.format("%.2f", roundOff));
        
        //Total
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE_SEVEN);
        billContent.add(PrintBillForm.TOTAL);
        billContent.add(String.format("%.2f", total));
        
        //Bill DetailPaid
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE_SEVEN);
        billContent.add(PrintBillForm.PAID);
        billContent.add(billDetailPaid);        
        
        //Bill Detail Balance
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.TAB_SPACE_SEVEN);
        billContent.add(PrintBillForm.BALANCE);
        billContent.add(billDetailBalance);
        
        //Disclaimer
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.DISCLAIMER);
        
        //Come Again
        billContent.add(PrintBillForm.NEW_LINE);
        billContent.add(PrintBillForm.COME_AGAIN);
        
        billContent.add(PrintBillForm.NEW_LINE);
        jTextAreaBill.setText("");
        for(String s: billContent) {
            jTextAreaBill.append(s);
        }
    }

    private void jTextAreaBillDocumentListener(){
        jTextAreaBill.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                if(jTextAreaBill.getText().trim().equals("")) {
                    isBillReady=false;
                } else {
                    isBillReady=true;
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(jTextAreaBill.getText().trim().equals("")){
                    isBillReady=false;
                }else {
                    isBillReady=true;
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(jTextAreaBill.getText().trim().equals("")) {
                    isBillReady = false;
                } else {
                    isBillReady = true;
                }
            }
        });
    }

    private void quit() {
        dispose();
    }

    private void customCode() {
        jTextAreaBillDocumentListener();
        prepareBill();
        jButtonPrint.requestFocus();
    }
    
    private void updateStocks() {
        for(int i=0; i< dtm.getRowCount(); i++) {
            String code = dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_CODE).toString();
            int quantity = Integer.parseInt(dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_QUANTITY).toString());
            try{
                ProductModel.updateStocks(code, quantity);
            } catch(Exception ex) {ex.printStackTrace();}
        }
    }

    public PrintBillForm(DefaultTableModel dtm, String billDetailItemCount, String billDetailQuantityCount, String billDetailTax, String billDetailTaxAmount, String billDetailTotal,String billDetailPaid,String billDetailBalance,String billDetailCustomerName) {
        this.dtm = dtm;
        this.billDetailItemCount = billDetailItemCount;
        this.billDetailQuantityCount = billDetailQuantityCount;
        this.billDetailTax = billDetailTax;
        this.billDetailTaxAmount = billDetailTaxAmount;
        this.billDetailSubTotal = billDetailTotal;
        this.billDetailPaid = billDetailPaid;
        this.billDetailBalance = billDetailBalance;
        this.billDetailCustomerName = billDetailCustomerName;
        initComponents();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });
        customCode();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelPrintBill = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaBill = new javax.swing.JTextArea();
        jButtonPrint = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("De El - Print Preview");

        jTextAreaBill.setColumns(20);
        jTextAreaBill.setRows(5);
        jScrollPane1.setViewportView(jTextAreaBill);

        jButtonPrint.setText("Print");
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });
        jButtonPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonPrintKeyPressed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelPrintBillLayout = new javax.swing.GroupLayout(jPanelPrintBill);
        jPanelPrintBill.setLayout(jPanelPrintBillLayout);
        jPanelPrintBillLayout.setHorizontalGroup(
            jPanelPrintBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrintBillLayout.createSequentialGroup()
                .addGroup(jPanelPrintBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPrintBillLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
                    .addGroup(jPanelPrintBillLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jButtonPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelPrintBillLayout.setVerticalGroup(
            jPanelPrintBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrintBillLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanelPrintBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPrint)
                    .addComponent(jButtonCancel))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelPrintBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelPrintBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
        doPrint();
    }//GEN-LAST:event_jButtonPrintActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        quit();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonPrintKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            quit();
        } else if(evt.getKeyCode() == KeyEvent.VK_ENTER) {            
            doPrint();
        }
    }//GEN-LAST:event_jButtonPrintKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JPanel jPanelPrintBill;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaBill;
    // End of variables declaration//GEN-END:variables

}

class PrintBill implements Printable{
    ArrayList<String> billContent;
    
    protected PrintBill(ArrayList<String> billContent){
        this.billContent = billContent;
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex != 0){
            return NO_SUCH_PAGE;
        }
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setFont(new Font("Serif", Font.PLAIN, 10));
        g2.setPaint(Color.BLACK);
        int y = 10;
        String line = "";
        for(String s: billContent){
            if(!s.equals("\n")) {
                if(s.equals(PrintBillForm.FONT_BOLD_OPEN)) {
                    g2.setFont(new Font("Serif", Font.BOLD, 13));
                    continue;
                } else if(s.equals(PrintBillForm.FONT_BOLD_CLOSE)) {
                    continue;
                } else if(s.equals(PrintBillForm.FONT_ITALIC_OPEN)) {
                    g2.setFont(new Font("Serif", Font.ITALIC , 10));
                    continue;
                } else if(s.equals(PrintBillForm.FONT_ITALIC_CLOSE)) {
                    continue;
                }
                line += s;
            } else {
                g2.drawString(line, 0, y);
                g2.setFont(new Font("Serif", Font.PLAIN, 10));
                line = "";
                y += 10;
            }
        }
        g2.drawString(".", 0, y + 50);
        return PAGE_EXISTS;
    }    
}