/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deel.view;

import com.deel.model.ProductModel;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Shankar Ram (shankar2k5@gmail.com)
 */
public class AddProductForm extends javax.swing.JFrame {
    
    private void addProduct() {
        String code = jTextFieldCode.getText();
        String category = jTextFieldCategory.getText();
        String brand = jTextFieldBrand.getText();
        String image = jTextFieldImage.getText();
        String stock = jTextFieldStock.getText();
        String price = jTextFieldPrice.getText();
        String meta = jTextFieldMeta.getText();
        String description = jTextAreaDescription.getText();
        boolean result = ProductModel.addProduct(code, category, brand, image, stock, price, meta, description);
        if(result) {
            JOptionPane.showMessageDialog(null, "Product added successfully", "Product Added", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        }else {
            JOptionPane.showMessageDialog(null, "Unable to add Product", "Unable To Add Product", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void quit() {
        dispose();
    }
    
    private void clearFields() {
        jTextFieldCode.setText("");
        jTextFieldCategory.setText("");
        jTextFieldBrand.setText("");
        jTextFieldImage.setText("");
        jTextFieldStock.setText("");
        jTextFieldPrice.setText("");
        jTextFieldMeta.setText("");
        jTextAreaDescription.setText("");
        jButtonSubmit.setEnabled(false);
    }
    
    private void customCode() {
        
    }
    
    public AddProductForm() {
        initComponents();
        customCode();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelCode = new javax.swing.JLabel();
        jTextFieldCode = new javax.swing.JTextField();
        jLabelCategory = new javax.swing.JLabel();
        jTextFieldCategory = new javax.swing.JTextField();
        jLabelBrand = new javax.swing.JLabel();
        jTextFieldBrand = new javax.swing.JTextField();
        jLabelImage = new javax.swing.JLabel();
        jTextFieldImage = new javax.swing.JTextField();
        jLabelStock = new javax.swing.JLabel();
        jTextFieldStock = new javax.swing.JTextField();
        jLabelPrice = new javax.swing.JLabel();
        jTextFieldPrice = new javax.swing.JTextField();
        jLabelMeta = new javax.swing.JLabel();
        jTextFieldMeta = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDescription = new javax.swing.JTextArea();
        jButtonSubmit = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("De El - Add Product");

        jLabelCode.setText("Code");

        jLabelCategory.setText("Category");

        jLabelBrand.setText("Brand");

        jLabelImage.setText("Image");

        jLabelStock.setText("Stock");

        jLabelPrice.setText("Price");

        jLabelMeta.setText("Meta");

        jLabel1.setText("Description");

        jTextAreaDescription.setColumns(20);
        jTextAreaDescription.setRows(5);
        jScrollPane1.setViewportView(jTextAreaDescription);

        jButtonSubmit.setText("Submit");
        jButtonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSubmitActionPerformed(evt);
            }
        });
        jButtonSubmit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonSubmitKeyPressed(evt);
            }
        });

        jButtonClear.setText("Clear");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });
        jButtonClear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonClearKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabelMeta)
                            .addComponent(jLabelPrice)
                            .addComponent(jLabelStock)
                            .addComponent(jLabelImage)
                            .addComponent(jLabelBrand))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldMeta, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(jTextFieldPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(jTextFieldStock, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(jTextFieldImage, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(jTextFieldBrand, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(jTextFieldCategory)
                            .addComponent(jTextFieldCode, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)))
                    .addComponent(jLabelCategory)
                    .addComponent(jLabelCode))
                .addGap(89, 89, 89))
            .addGroup(layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jButtonSubmit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonClear)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCode))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCategory)
                    .addComponent(jTextFieldCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBrand)
                    .addComponent(jTextFieldBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelImage)
                    .addComponent(jTextFieldImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStock)
                    .addComponent(jTextFieldStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPrice)
                    .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMeta)
                    .addComponent(jTextFieldMeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSubmit)
                    .addComponent(jButtonClear))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(365, 546));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        clearFields();
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonClearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonClearKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            clearFields();
        } else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            quit();
        }
    }//GEN-LAST:event_jButtonClearKeyPressed

    private void jButtonSubmitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonSubmitKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addProduct();
        } else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            quit();
        }
    }//GEN-LAST:event_jButtonSubmitKeyPressed

    private void jButtonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubmitActionPerformed
        addProduct();
    }//GEN-LAST:event_jButtonSubmitActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelBrand;
    private javax.swing.JLabel jLabelCategory;
    private javax.swing.JLabel jLabelCode;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JLabel jLabelMeta;
    private javax.swing.JLabel jLabelPrice;
    private javax.swing.JLabel jLabelStock;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextFieldBrand;
    private javax.swing.JTextField jTextFieldCategory;
    private javax.swing.JTextField jTextFieldCode;
    private javax.swing.JTextField jTextFieldImage;
    private javax.swing.JTextField jTextFieldMeta;
    private javax.swing.JTextField jTextFieldPrice;
    private javax.swing.JTextField jTextFieldStock;
    // End of variables declaration//GEN-END:variables
}