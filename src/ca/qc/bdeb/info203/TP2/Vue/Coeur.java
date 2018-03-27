/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.TP2.Vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author 1529311
 */
public class Coeur extends JComponent {

    Image img = Toolkit.getDefaultToolkit().getImage("coeur.gif");

    public Coeur() {
        setSize(20, 18);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }

}
