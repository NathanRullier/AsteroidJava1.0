/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.TP2.Vue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.JComponent;

/**
 *
 * @author 1529311
 */
public class GrosCailloux extends JComponent {

    private Random rng = new Random();
    private int choixCaillou;
    private int vitesseX = 0;
    private int vitesseY = 0;
    private double deceleration = 1.05;
    private double degres = 0;
    private Image img;
    private boolean touchable = true;

    public GrosCailloux(int vitesseX, int vitesseY) {
        choixCaillou = rng.nextInt(2);
        if (choixCaillou == 0) {
            img = Toolkit.getDefaultToolkit().getImage("asteroid1.gif");
            setSize(37, 36);
        } else if (choixCaillou == 1) {
            img = Toolkit.getDefaultToolkit().getImage("asteroid2.gif");
            setSize(53, 52);
        }
        this.vitesseX = vitesseX;
        this.vitesseY = vitesseY;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }

    public void touche() {
        touchable = false;
    }
    public boolean getTouchable(){
        return touchable;
    }
    void majGrosCaillou(int longeur, int hauteur) {
        this.setLocation(this.getX() + vitesseX, this.getY() + vitesseY);
        if (this.getX() >= longeur + 32) {
            this.setLocation(0, this.getY());
        } else if (this.getX() <= -32) {
            this.setLocation(longeur, this.getY());
        }
        if (this.getY() >= hauteur + 32) {
            this.setLocation(this.getX(), 0);
        } else if (this.getY() <= -32) {
            this.setLocation(this.getX(), hauteur);
        }
    }
    
    public int getChoixCaillou(){
        return choixCaillou;
    }
}
