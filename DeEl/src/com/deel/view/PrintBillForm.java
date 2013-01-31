package com.deel.view;

import com.deel.model.ProductModel;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author neo
 */
public class PrintBillForm extends javax.swing.JFrame {
    private DefaultTableModel dtm;
    private boolean isBillReady;
    private boolean isPrintSuccess;
    private static final String NEW_LINE="\n";
    private static final String COMPANY_NAME="De El";
    private static final String ADDRESS="\n 61, Annai Towers,\n Annai Velankanni Church Road,\n Beasnt Nagar, Chennai 600020";
    private static final String PHONE_NUMBER="\n Phone: 044-24915144, 044-24900062";
    private static final String TIN_NUMBER="\n Tin: 33190860765";
    private static final String SERIAL_NUMBER="\n Sr. No.";
    private static final String NAME="\t Name";
    private static final String PRICE="\t Price";
    private static final String QUANTITY="\t Quantity";
    private static final String AMOUNT="\t Amount";
    private ArrayList<String> defaultsToBePrinted;

    private void preparePrintBill(){
        defaultsToBePrinted=new ArrayList();
        defaultsToBePrinted.add(PrintBillForm.COMPANY_NAME);
        defaultsToBePrinted.add(PrintBillForm.ADDRESS);
        defaultsToBePrinted.add(PrintBillForm.PHONE_NUMBER);
        defaultsToBePrinted.add(PrintBillForm.TIN_NUMBER);
        defaultsToBePrinted.add(PrintBillForm.NEW_LINE);
        defaultsToBePrinted.add(PrintBillForm.SERIAL_NUMBER);
        defaultsToBePrinted.add(PrintBillForm.NAME);
        defaultsToBePrinted.add(PrintBillForm.PRICE);
        defaultsToBePrinted.add(PrintBillForm.QUANTITY);
        defaultsToBePrinted.add(PrintBillForm.AMOUNT);
        jTextAreaPrintBill.setText("");
        for(String s:defaultsToBePrinted){
            jTextAreaPrintBill.append(s);
        }
    }

    private void jTextAreaPrintBillDocumentListener(){
        jTextAreaPrintBill.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                if(jTextAreaPrintBill.getText().trim().equals("")){
                    isBillReady=false;
                }else{
                    isBillReady=true;
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(jTextAreaPrintBill.getText().trim().equals("")){
                    isBillReady=false;
                }else{
                    isBillReady=true;
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(jTextAreaPrintBill.getText().trim().equals("")){
                    isBillReady=false;
                }else{
                    isBillReady=true;
                }
            }
        });
    }

    private void quit(){
        dispose();
    }

    private void customCode(){
        jTextAreaPrintBillDocumentListener();
        jPanelPrintBill.requestFocus();
        preparePrintBill();
    }
    private void updateStocks(){
        for(int i=0; i< dtm.getRowCount(); i++){
            new ProductModel().updateStocks(dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_CODE).toString(), Integer.parseInt(dtm.getValueAt(i, BillerForm.TABLE_BILL_DETAIL_COLUMN_QUANTITY).toString()));
        }
    }

    public PrintBillForm(DefaultTableModel dtm,String total,String paid,String balace,String customerName) {
        this.dtm=dtm;
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
        jTextAreaPrintBill = new javax.swing.JTextArea();
        jButtonPrint = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextAreaPrintBill.setColumns(20);
        jTextAreaPrintBill.setRows(5);
        jScrollPane1.setViewportView(jTextAreaPrintBill);

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
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanelPrintBillLayout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jButtonPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
        );
        jPanelPrintBillLayout.setVerticalGroup(
            jPanelPrintBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrintBillLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelPrintBillLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonPrint)
                    .addComponent(jButtonCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        if(isBillReady){
            try{
                isPrintSuccess=jTextAreaPrintBill.print();
                if(isPrintSuccess){
                    javax.swing.JOptionPane.showMessageDialog(null, "Success", "Bill sent for printing", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    updateStocks();
                }else{
                    javax.swing.JOptionPane.showMessageDialog(null, "Failure", "Printing failure", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }catch(Exception ex){ex.printStackTrace();}
        }else{
            javax.swing.JOptionPane.showMessageDialog(null, "Nothing to print", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonPrintActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        quit();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonPrintKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            quit();
        }
    }//GEN-LAST:event_jButtonPrintKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JPanel jPanelPrintBill;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaPrintBill;
    // End of variables declaration//GEN-END:variables

}