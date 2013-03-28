package com.deel.test;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author neo
 */
public class JTextPaneExample {
    private JTextPane jtp;
    private JFrame jf;
    
    public JTextPaneExample() {
        try{
            jf = new JFrame();
            jtp = new JTextPane();
            SimpleAttributeSet sas = new SimpleAttributeSet();
            StyleConstants.setAlignment(sas, StyleConstants.ALIGN_CENTER);
            StyleConstants.setBold(sas, true);
            StyleConstants.setItalic(sas, true);
            StyleConstants.setUnderline(sas, true);           
            Document doc = jtp.getDocument();
            doc.insertString(doc.getLength(), "hi", sas);
            jf.getContentPane().add(BorderLayout.CENTER, new JScrollPane(jtp));
            jf.pack();
            jf.setVisible(true);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }catch(Exception ex){ex.printStackTrace();}
    }
    
    public static void main(String[] args) {
        new JTextPaneExample();
    }            
}