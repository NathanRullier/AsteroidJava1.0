/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.TP2.Vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;

/**
 *
 * @author 1529311
 */
public class LaserPewPew extends JComponent {

    boolean out = false;
    private double degres;
    private int locX;
    private int locY;
    int player;
    int vitesse = 3;
    int vitesseX;
    int vitesseY;

    public LaserPewPew(double degres, int player, double locXd, double locYd) {
        setSize(5, 5);
        this.degres = degres;
        this.vitesseX = (int) Math.round(vitesse * Math.cos(degres));
        this.vitesseY = (int) Math.round(vitesse * Math.sin(degres));
        this.player = player;
        this.locX = (int) Math.round(locXd);
        this.locY = (int) Math.round(locYd);
        this.setLocation(locX, locY);

    }

    LaserPewPew(double degres, int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean getOut(){
        return out;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (player == 1) {
            g.setColor(Color.RED);
        } else if (player == 2) {
            g.setColor(Color.CYAN);
        }
        g.fillOval(0, 0, 5, 5);

    }
    

    public void bougerLaser(int longeur, int hauteur) {
        this.setLocation(this.getX() + vitesseX, this.getY() + vitesseY);
        if (this.getX() >= longeur +30) {
            out = true;
            
        } else if (this.getX() <= -30) {
            out = true;
           
        }
        if (this.getY() >= hauteur+30) {
            out = true;
            
        } else if (this.getY() <= -30) {
            out = true;
            
        }
    }

}
