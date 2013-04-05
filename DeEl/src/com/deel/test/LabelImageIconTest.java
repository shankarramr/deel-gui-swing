package com.deel.test;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author neo
 */

public class LabelImageIconTest {
    public static void main(String[] args) {
        JLabel jl = new JLabel();
        try {
            jl.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("zzz"))));
        } catch(Exception ex) {ex.printStackTrace();}
    }
}