package com.deel.view;

import java.awt.event.ItemEvent;
import java.util.HashMap;

/**
 *
 * @author neo
 */
public class PreferenceForm extends javax.swing.JFrame {
    private HashMap<String,Boolean> preferenceMap;

    private void customCode(){
        preferenceMap=new HashMap();
        dynamicSearchGroup.add(jRadioButtonDynamicSearchOn);
        dynamicSearchGroup.add(jRadioButtonDynamicSearchOff);
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
        jCheckBoxTax = new javax.swing.JCheckBox();
        jButtonSubmit = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jRadioButtonDynamicSearchOn = new javax.swing.JRadioButton();
        jRadioButtonDynamicSearchOff = new javax.swing.JRadioButton();
        jLabelDynamicSearch = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jCheckBoxTax.setText("Tax");
        jCheckBoxTax.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxTaxItemStateChanged(evt);
            }
        });

        jButtonSubmit.setText("Submit");
        jButtonSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSubmitActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");

        jRadioButtonDynamicSearchOn.setText("On");
        jRadioButtonDynamicSearchOn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonDynamicSearchOnItemStateChanged(evt);
            }
        });

        jRadioButtonDynamicSearchOff.setText("Off");
        jRadioButtonDynamicSearchOff.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonDynamicSearchOffItemStateChanged(evt);
            }
        });

        jLabelDynamicSearch.setText("DynamicSearch");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelDynamicSearch)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonDynamicSearchOn))
                            .addComponent(jButtonSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonDynamicSearchOff)
                            .addComponent(jButtonCancel)))
                    .addComponent(jCheckBoxTax))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxTax)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonDynamicSearchOn)
                    .addComponent(jRadioButtonDynamicSearchOff)
                    .addComponent(jLabelDynamicSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSubmit)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBoxTaxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxTaxItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED){
            preferenceMap.put("tax", true);
        }else if(evt.getStateChange() == ItemEvent.DESELECTED){
            preferenceMap.put("tax", false);
        }
    }//GEN-LAST:event_jCheckBoxTaxItemStateChanged

    private void jRadioButtonDynamicSearchOnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonDynamicSearchOnItemStateChanged
        preferenceMap.put("dynamicSearch", true);
    }//GEN-LAST:event_jRadioButtonDynamicSearchOnItemStateChanged

    private void jRadioButtonDynamicSearchOffItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonDynamicSearchOffItemStateChanged
        preferenceMap.put("dynamicSearch", false);
    }//GEN-LAST:event_jRadioButtonDynamicSearchOffItemStateChanged

    private void jButtonSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubmitActionPerformed
        return;
    }//GEN-LAST:event_jButtonSubmitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup dynamicSearchGroup;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonSubmit;
    private javax.swing.JCheckBox jCheckBoxTax;
    private javax.swing.JLabel jLabelDynamicSearch;
    private javax.swing.JRadioButton jRadioButtonDynamicSearchOff;
    private javax.swing.JRadioButton jRadioButtonDynamicSearchOn;
    // End of variables declaration//GEN-END:variables

}