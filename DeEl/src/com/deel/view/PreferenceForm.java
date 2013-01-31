package com.deel.view;

import java.util.HashMap;

/**
 *
 * @author neo
 */
public class PreferenceForm extends javax.swing.JFrame {
    private HashMap<String,Boolean> preferenceMap;

    private void customCode(){
        preferenceMap=new HashMap();
    }

    public PreferenceForm() {
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

        dynamicSearchGroup = new javax.swing.ButtonGroup();
        jButtonSubmit = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabelDynamicSearch = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButtonTax = new javax.swing.JToggleButton();
        jLabelTax = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonSubmit.setText("Submit");
        jButtonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSubmitActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");

        jLabelDynamicSearch.setText("Dynamic Search");

        jLabelTax.setText("Tax");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelDynamicSearch)
                            .addComponent(jLabelTax, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jToggleButtonTax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(91, 91, 91))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonCancel)
                        .addGap(69, 69, 69))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabelDynamicSearch))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jToggleButtonTax, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabelTax)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSubmit)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubmitActionPerformed
        return;
    }//GEN-LAST:event_jButtonSubmitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup dynamicSearchGroup;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonSubmit;
    private javax.swing.JLabel jLabelDynamicSearch;
    private javax.swing.JLabel jLabelTax;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButtonTax;
    // End of variables declaration//GEN-END:variables

}