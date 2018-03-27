/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.TP2.Vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JComponent;

/**
 *
 * @author 1529311
 */
public class Planete extends JComponent {

    private Image img;

    public Planete(String path) {
        img = Toolkit.getDefaultToolkit().getImage(path);
        setSize(400, 400);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        g.drawImage(img, 0, 0, this);
    }

}
