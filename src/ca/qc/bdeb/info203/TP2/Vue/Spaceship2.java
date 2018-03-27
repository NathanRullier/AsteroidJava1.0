/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.TP2.Vue;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author 1529311
 */
public class Spaceship2 extends JComponent implements Spaceship {

    private boolean touchable = true;
    private int vitesseX = 0;
    private int vitesseY = 0;
    private int nbrClignote = 0;
    private int clignotement = 5;
    private double acceleration = 2;
    private double deceleration = 1.05;
    private BufferedImage img;
    private double degres = 0;

    public Spaceship2() {
        try {
            this.img = ImageIO.read(new File("vaisseau1right.gif"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "l'image vaisseau2left.gif n'existe pas");
        }
        setSize(64, 64);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform tx = AffineTransform.getRotateInstance(degres, 32.5, 32);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage((op.filter(img, null)), 0, 0, this);
        //draw shape/image (will be rotated)

        //things you draw after here will not be rotated
    }

    void majVaisseau(int longeur, int hauteur) {
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

    void tournerDroite() {
        degres = degres + Math.PI / 32;

    }

    void tournerGauche() {
        degres = degres - Math.PI / 32;

    }

    void accelerer() {
        vitesseX = vitesseX + (int) Math.round(acceleration * Math.cos(degres));
        vitesseY = vitesseY + (int) Math.round(acceleration * Math.sin(degres));
        if (vitesseX >= 8) {
            vitesseX = 8;
        } else if (vitesseX <= -8) {
            vitesseX = -8;
        }
        if (vitesseY >= 8) {
            vitesseY = 8;
        } else if (vitesseY <= -8) {
            vitesseY = -8;
        }
    }

    void deceleration() {

        vitesseX = (int) (vitesseX / deceleration);
        vitesseY = (int) (vitesseY / deceleration);

    }

    public double getDegres() {
        return degres;
    }

    void hit() {
        touchable = false;
        clignotement++;
        if (clignotement == 15) {
            if (this.isVisible()) {
                this.setVisible(false);
                nbrClignote++;
            } else {
                this.setVisible(true);
                nbrClignote++;
            }
            clignotement = 0;
        }
        if (nbrClignote == 8) {
            touchable = true;
            nbrClignote = 0;
        }
    }

    public boolean getTouchable() {
        return touchable;
    }
}
