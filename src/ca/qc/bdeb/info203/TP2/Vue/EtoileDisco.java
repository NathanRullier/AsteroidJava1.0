/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.TP2.Vue;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JComponent;

/**
 *
 * @author 1529311
 */
public class EtoileDisco extends JComponent {

    private Random rng = new Random();

    public EtoileDisco() {
        setSize(20, 20);
        this.setLocation(rng.nextInt(1500), rng.nextInt(844));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255)));
        g.fillOval(0, 0, 3, 3);
    }

    public void disco() {

        revalidate();
        repaint();
    }

}
