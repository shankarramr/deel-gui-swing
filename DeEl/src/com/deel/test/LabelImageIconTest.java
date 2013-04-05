package com.deel.test;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

/**
 *
 * @author Shankar Ram (shankar2k5@gmail.com)
 */

public class LabelImageIconTest {
    public static void main(String[] args) {
        JLabel jl = new JLabel();
        try {
            jl.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("zzz"))));
        } catch(Exception ex) {ex.printStackTrace();}
    }
}