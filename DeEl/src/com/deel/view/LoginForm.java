package com.deel.view;

import com.deel.model.Login;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author neo
 */
public class LoginForm extends javax.swing.JFrame {
    public void onLogin(){
        if(new Login().authenticate(jTextFieldUsername.getText(), new String(jPasswordFieldPassword.getPassword()))){
            dispose();
            new BillerForm();
        }else{
            JOptionPane.showMessageDialog(null, "Invalid username or password", "Invalid", JOptionPane.ERROR_MESSAGE);
            jPasswordFieldPassword.requestFocus();
        }
    }

    public LoginForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelUsername = new javax.swing.JLabel();
        jTextFieldUsername = new javax.swing.JTextField();
        jLabelPassword = new javax.swing.JLabel();
        jPasswordFieldPassword = new javax.swing.JPasswordField();
        jButtonLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("De El");
        setResizable(false);

        jLabelUsername.setText("Username");
        jLabelUsername.setPreferredSize(new java.awt.Dimension(60, 15));

        jTextFieldUsername.setText("Enter your username");
        jTextFieldUsername.setPreferredSize(new java.awt.Dimension(150, 25));
        jTextFieldUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldUsernameFocusGained(evt);
            }
        });
        jTextFieldUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldUsernameKeyPressed(evt);
            }
        });

        jLabelPassword.setText("Password");

        jPasswordFieldPassword.setText("Enter your password");
        jPasswordFieldPassword.setPreferredSize(new java.awt.Dimension(150, 25));
        jPasswordFieldPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordFieldPasswordFocusGained(evt);
            }
        });
        jPasswordFieldPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordFieldPasswordKeyPressed(evt);
            }
        });

        jButtonLogin.setText("Login");
        jButtonLogin.setMaximumSize(new java.awt.Dimension(60, 30));
        jButtonLogin.setMinimumSize(new java.awt.Dimension(60, 30));
        jButtonLogin.setPreferredSize(new java.awt.Dimension(60, 30));
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });
        jButtonLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonLoginKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(jLabelUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPassword)
                    .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

    private void jPasswordFieldPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordFieldPasswordFocusGained
        // TODO add your handling code here:
        jPasswordFieldPassword.selectAll();
    }//GEN-LAST:event_jPasswordFieldPasswordFocusGained

    private void jTextFieldUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldUsernameFocusGained
        // TODO add your handling code here:
        jTextFieldUsername.selectAll();
    }//GEN-LAST:event_jTextFieldUsernameFocusGained

    private void jPasswordFieldPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldPasswordKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            onLogin();
        }else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }//GEN-LAST:event_jPasswordFieldPasswordKeyPressed

    private void jTextFieldUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldUsernameKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            onLogin();
        }else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }//GEN-LAST:event_jTextFieldUsernameKeyPressed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        // TODO add your handling code here:
        onLogin();
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonLoginKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }//GEN-LAST:event_jButtonLoginKeyPressed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JPasswordField jPasswordFieldPassword;
    private javax.swing.JTextField jTextFieldUsername;
    // End of variables declaration//GEN-END:variables
}
