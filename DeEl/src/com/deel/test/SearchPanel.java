package com.deel.test;

import com.deel.model.ProductModel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 *
 * @author neo
 */
public class SearchPanel extends javax.swing.JPanel {
    private JTextComponent jtc;
    private List<String> productList;

    private void onSearch(){
        jComboBoxSearch.getSelectedItem();
    }

    //This is the module for static search.
    public void fetchAllProduct(){
        productList=new ProductModel().fetchAllProduct();
        new Thread(new Runnable(){
            @Override
           public void run(){
               for(String item:productList){
                   jComboBoxSearch.addItem(item);
               }
           }
        }).start();
    }

    //This is the module for dynamic search which is under construction till then using static model(the one above).
    private void jComboBoxContentChange(String q){
        final String q1=q;
        if(q.length()>=3){
            productList=new ProductModel().dynamicSearch(q);
            if(productList != null){
                new Thread(new Runnable(){
                    @Override
                   public void run(){
                        jComboBoxSearch.removeAllItems();
                        for(String item:productList){
                            jComboBoxSearch.addItem(item);
                        }
                    }
                }).start();
            }
        }else{
            new Thread(new Runnable(){
                @Override
                public void run(){
                    jComboBoxSearch.removeAllItems();
                }
            });
        }
    }

    private void customCode(){
        jComboBoxSearch.setModel(new DefaultComboBoxModel());
        jtc=(JTextComponent)jComboBoxSearch.getEditor().getEditorComponent();
        jtc.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                //jComboBoxContentChange(jtc.getText(););
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //jComboBoxContentChange(jtc.getText(););
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
        //fetchAllProduct();
    }

    public SearchPanel() {
        initComponents();
        customCode();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBoxSearch = new javax.swing.JComboBox();
        jButtonSearch = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(400, 30));

        jComboBoxSearch.setEditable(true);
        jComboBoxSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSearchActionPerformed(evt);
            }
        });

        jButtonSearch.setText("Search");
        jButtonSearch.setFocusable(false);
        jButtonSearch.setRequestFocusEnabled(false);
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearch))
                .addGap(0, 3, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        onSearch();
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jComboBoxSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSearchActionPerformed
        onSearch();
    }//GEN-LAST:event_jComboBoxSearchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JComboBox jComboBoxSearch;
    // End of variables declaration//GEN-END:variables
}
