/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.TP2.Vue;

import ca.qc.bdeb.info203.TP2.Controller.Controller;
import ca.qc.bdeb.info203.TP2.Modele.Modele;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.html.CSS;

/**
 *
 * @author 1529311
 */
public class Monde extends JPanel implements Runnable, Observer {

    private Random rng = new Random();
    private Controller controller;
    private Modele modele;
    private boolean droitTirerP1 = true;
    private boolean droitTirerP2 = true;
    private int cooldownP1 = 0;
    private int cooldownP2 = 0;
    private int nbrPointP1 = 0;
    private int nbrPointP2 = 0;
    private int nbrTirP1 = 0;
    private int nbrTirP2 = 0;
    private int nbrTirP1Hit = 0;
    private int nbrTirP2Hit = 0;
    private double p1HitPrc;
    private double p2HitPrc;
    private boolean stop = false;
    private ArrayList<GrosCailloux> listeGrosCaillouAEnlever = new ArrayList();
    private ArrayList<GrosCailloux> listeGrosCaillou = new ArrayList();
    private ArrayList<Integer> listeKeyCodes = new ArrayList<>();
    private Image img = Toolkit.getDefaultToolkit().getImage("background2.jpg");
    private Image img2 = Toolkit.getDefaultToolkit().getImage("planetkek.gif");
    private Image img3 = Toolkit.getDefaultToolkit().getImage("planetk3k.png");
    private ArrayList<EtoileDisco> arrayEtoile = new ArrayList();
    private ArrayList<LaserPewPew> laserP1 = new ArrayList();
    private ArrayList<LaserPewPew> laserP2 = new ArrayList();
    private ArrayList<LaserPewPew> laserAEnlever = new ArrayList();

    private JLabel lblP1Point;
    private JLabel lblP2Point;

    private Background background = new Background();
    private Spaceship1 spaceship1 = new Spaceship1();
    private Spaceship2 spaceship2 = new Spaceship2();
    private ArrayList<Coeur> coeurP1 = new ArrayList();
    private ArrayList<Coeur> coeurP2 = new ArrayList();
    private Coeur coeur = new Coeur();
    private int nbrVieP1 = 3;
    private int nbrVieP2 = 3;

    public Monde(Modele modele, Controller controller) {
        this.modele = modele;
        this.controller = controller;

        JLabel lblP1 = new JLabel("Joueur 1 : ");
        lblP1.setForeground(Color.WHITE);
        lblP1.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        this.add(lblP1).setSize(150, 30);

        JLabel lblP2 = new JLabel("Joueur 2 : ");
        lblP2.setForeground(Color.WHITE);
        lblP2.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        lblP2.setLocation(1100, 0);
        this.add(lblP2).setSize(150, 30);

        lblP1Point = new JLabel("Score : " + nbrPointP1);
        lblP1Point.setForeground(Color.WHITE);
        lblP1Point.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        lblP1Point.setLocation(0, 35);
        this.add(lblP1Point).setSize(1500, 30);

        lblP2Point = new JLabel("Score : " + nbrPointP2);
        lblP2Point.setForeground(Color.WHITE);
        lblP2Point.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        lblP2Point.setLocation(1100, 35);
        this.add(lblP2Point).setSize(1500, 30);

        for (int i = 0; i < nbrVieP1; i++) {
            Coeur coeur = new Coeur();
            this.add(coeur).setLocation(140 + 30 * i, 10);
            coeurP1.add(coeur);
        }
        for (int i = 0; i < nbrVieP2; i++) {
            Coeur coeur = new Coeur();
            this.add(coeur).setLocation(1250 + 30 * i, 10);
            coeurP2.add(coeur);
        }
        this.add(spaceship2).setLocation(1000, 100);
        this.add(spaceship1).setLocation(100, 100);

        for (int i = 0; i < 200; i++) {
            EtoileDisco etoileDisco = new EtoileDisco();
            arrayEtoile.add(etoileDisco);
            this.add(etoileDisco);
        }
        for (int i = 0; i < 5; i++) {
            GrosCailloux grosCaillou = new GrosCailloux(rng.nextInt(10) - 5, rng.nextInt(10) - 5);

            this.add(grosCaillou).setLocation(rng.nextInt(1113), rng.nextInt(627));
            listeGrosCaillou.add(grosCaillou);

        }
        new Thread(this).start();
        setSize(1113, 627);
        setLayout(null);
    }

    public void hitbox() {
        for (LaserPewPew laser : laserP1) {
            if (spaceship2.getTouchable() == true && laser.getBounds().intersects(spaceship2.getBounds())) {
                nbrTirP1Hit++;
                spaceship2.hit();
                controller.sp2Hit();
                
                
                for (LaserPewPew laser2 : laserP1) {
                    laser2.setVisible(false);
                }
                laserAEnlever.add(laser);
            }
        }
        for (LaserPewPew laser : laserP2) {
            if (spaceship1.getTouchable() == true && laser.getBounds().intersects(spaceship1.getBounds())) {
                nbrTirP2Hit++;
                spaceship1.hit();
                controller.sp1Hit();
                
                for (LaserPewPew laser2 : laserP2) {
                    laser2.setVisible(false);
                }
                laserAEnlever.add(laser);
            }
        }

        for (GrosCailloux caillou : listeGrosCaillou) {
            if (caillou.getBounds().intersects(spaceship1.getBounds())) {
                if (spaceship1.getTouchable() == true && caillou.getTouchable() == true) {
                    spaceship1.hit();
                    controller.sp1Hit();
                }
            }
            for (LaserPewPew laser : laserP1) {
                if (caillou.getBounds().intersects(laser.getBounds())) {
                    if (!listeGrosCaillouAEnlever.contains(caillou)) {
                        listeGrosCaillouAEnlever.add(caillou);
                        if (caillou.getChoixCaillou() == 0) {
                            controller.sp1Point(100);
                            nbrTirP1Hit++;
                        } else if (caillou.getChoixCaillou() == 1) {
                            controller.sp1Point(50);
                            nbrTirP1Hit++;
                        }
                    }
                    laserAEnlever.add(laser);

                    for (LaserPewPew laser2 : laserP1) {
                        laser2.setVisible(false);
                    }

                    caillou.touche();
                }
            }
            if (caillou.getBounds().intersects(spaceship2.getBounds())) {
                if (spaceship2.getTouchable() == true && caillou.getTouchable() == true) {
                    spaceship2.hit();
                    controller.sp2Hit();
                }
            }
            for (LaserPewPew laser : laserP2) {
                if (caillou.getBounds().intersects(laser.getBounds())) {
                    if (!listeGrosCaillouAEnlever.contains(caillou)) {
                        listeGrosCaillouAEnlever.add(caillou);
                        if (caillou.getChoixCaillou() == 0) {
                            controller.sp2Point(100);
                            nbrTirP2Hit++;
                        } else if (caillou.getChoixCaillou() == 1) {
                            controller.sp2Point(50);
                            nbrTirP2Hit++;
                        }
                    }
                    laserAEnlever.add(laser);

                    for (LaserPewPew laser2 : laserP2) {
                        laser2.setVisible(false);
                    }

                    caillou.touche();
                }
            }

            for (LaserPewPew laser : laserAEnlever) {
                if (laserP1.contains(laser)) {
                    laserP1.clear();
                } else if (laserP2.contains(laser)) {
                    laserP2.clear();
                }

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        remove(laser);
                    }
                });
            }

            laserAEnlever.clear();
            for (GrosCailloux caillou1 : listeGrosCaillouAEnlever) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        remove(caillou1);
                    }
                });
            }
        }
        listeGrosCaillou.removeAll(listeGrosCaillouAEnlever);

        listeGrosCaillouAEnlever.clear();
        if (spaceship1.getTouchable() == false) {
            spaceship1.hit();
        }
        if (spaceship2.getTouchable() == false) {
            spaceship2.hit();
        }

    }

    public void MajMonde() {
        if (listeGrosCaillou.size() < 5) {
            grosCaillouSpawn();
            revalidate();
            repaint();
        }
        for (int i = 0; i < arrayEtoile.size(); i++) {
            arrayEtoile.get(i).disco();
        }
        for (int i = 0; i < laserP1.size(); i++) {
            for (int j = 0; j < 3; j++) {
                laserP1.get(i).bougerLaser(this.getWidth(), this.getHeight());
                if (laserP1.get(i).getOut() == true) {
                    laserP1.removeAll(laserP1);
                }
            }
        }
        for (int i = 0; i < laserP2.size(); i++) {
            for (int j = 0; j < 3; j++) {
                laserP2.get(i).bougerLaser(this.getWidth(), this.getHeight());
                if (laserP2.get(i).getOut() == true) {
                    laserP2.removeAll(laserP2);
                }
            }
        }
        for (int i = 0; i < listeGrosCaillou.size(); i++) {
            listeGrosCaillou.get(i).majGrosCaillou(this.getWidth(), this.getHeight());
        }
        spaceship2.majVaisseau(this.getWidth(), this.getHeight());
        spaceship1.majVaisseau(this.getWidth(), this.getHeight());
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        g.drawImage(img, 0, 0, this);
        g.drawImage(img2, 1000, 400, this);
        g.drawImage(img3, 20, 20, this);

    }

    @Override
    public void run() {
        while (stop == false) {
            try {
                Thread.sleep(25);
                MajMonde();
                if (listeKeyCodes.contains(KeyEvent.VK_A)) {
                    tournerDroite1();
                }
                if (listeKeyCodes.contains(KeyEvent.VK_D)) {
                    tournerGauche1();
                }
                if (listeKeyCodes.contains(KeyEvent.VK_W)) {
                    acceleration1();
                }
                if (listeKeyCodes.contains(KeyEvent.VK_S)) {
                    deceleration1();
                }
                if (listeKeyCodes.contains(KeyEvent.VK_SPACE)) {
                    pew1();
                }

                if (listeKeyCodes.contains(KeyEvent.VK_RIGHT)) {
                    tournerDroite2();
                }
                if (listeKeyCodes.contains(KeyEvent.VK_LEFT)) {
                    tournerGauche2();
                }
                if (listeKeyCodes.contains(KeyEvent.VK_UP)) {
                    acceleration2();
                }
                if (listeKeyCodes.contains(KeyEvent.VK_DOWN)) {
                    deceleration2();
                }
                if (listeKeyCodes.contains(KeyEvent.VK_NUMPAD0)) {
                    pew2();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Monde.class.getName()).log(Level.SEVERE, null, ex);
            }
            hitbox();

            refroidissementP1();
            refroidissementP2();

        }
    }

    void tournerGauche1() {
        spaceship1.tournerGauche();
    }

    void tournerDroite1() {
        spaceship1.tournerDroite();
    }

    void acceleration1() {
        spaceship1.accelerer();
    }

    void deceleration1() {
        spaceship1.deceleration();
    }

    void pew1() {

        if (droitTirerP1 == true) {
            nbrTirP1++;
            for (int i = 0; i < 25; i++) {
                LaserPewPew temp1 = new LaserPewPew(spaceship1.getDegres(), 1, spaceship1.getX() + 32 + 50 * i * (Math.toRadians(Math.cos(spaceship1.getDegres()))), spaceship1.getY() + 32 + 50 * i * (Math.toRadians(Math.sin(spaceship1.getDegres()))));
                this.add(temp1);
                laserP1.add(temp1);
            }
            droitTirerP1 = false;
        }

        revalidate();
        repaint();

    }

    public void setListeKeyCodes(ArrayList<Integer> listeKeyCodes) {
        this.listeKeyCodes = listeKeyCodes;
    }

    private void refroidissementP1() {
        if (droitTirerP1 == false) {
            cooldownP1++;
            if (cooldownP1 == 200) {
                droitTirerP1 = true;
                cooldownP1 = 0;
            }
        }

    }

    private void tournerDroite2() {
        spaceship2.tournerDroite();
    }

    private void tournerGauche2() {
        spaceship2.tournerGauche();
    }

    private void acceleration2() {
        spaceship2.accelerer();
    }

    private void deceleration2() {
        spaceship2.deceleration();
    }

    private void pew2() {

        if (droitTirerP2 == true) {
            nbrTirP2++;
            for (int i = 0; i < 25; i++) {
                LaserPewPew temp1 = new LaserPewPew(spaceship2.getDegres(), 2, spaceship2.getX() + 32 + 50 * i * (Math.toRadians(Math.cos(spaceship2.getDegres()))), spaceship2.getY() + 32 + 50 * i * (Math.toRadians(Math.sin(spaceship2.getDegres()))));
                this.add(temp1);
                laserP2.add(temp1);
            }
            droitTirerP2 = false;
        }

        revalidate();
        repaint();
    }

    private void refroidissementP2() {
        if (droitTirerP2 == false) {
            cooldownP2++;
            if (cooldownP2 == 200) {
                droitTirerP2 = true;
                cooldownP2 = 0;
            }
        }
    }

    private void grosCaillouSpawn() {

        int cote = rng.nextInt(4);
        GrosCailloux grosCailloux = new GrosCailloux(rng.nextInt(10) - 5, rng.nextInt(10) - 5);
        this.add(grosCailloux);
        if (cote == 0) {
            grosCailloux.setLocation(rng.nextInt(1113), -10);
        }
        if (cote == 1) {
            grosCailloux.setLocation(1123, rng.nextInt(627));
        }
        if (cote == 2) {
            grosCailloux.setLocation(rng.nextInt(1113), 637);
        }
        if (cote == 3) {
            grosCailloux.setLocation(-10, rng.nextInt(627));
        }
        setLocation(WIDTH, WIDTH);
        listeGrosCaillou.add(grosCailloux);
    }

    @Override
    public void update(java.util.Observable o, Object arg) {

        for (int i = 0; i < nbrVieP1; i++) {
            coeurP1.get(i).setVisible(false);
        }
        for (int i = 0; i < nbrVieP2; i++) {
            coeurP2.get(i).setVisible(false);
        }
        coeurP1.clear();
        coeurP2.clear();
        nbrVieP1 = modele.getNbrVieP1();
        nbrVieP2 = modele.getNbrVieP2();
        for (int i = 0; i < nbrVieP1; i++) {
            Coeur coeur = new Coeur();
            this.add(coeur).setLocation(140 + 30 * i, 10);
            coeurP1.add(coeur);
        }
        for (int i = 0; i < nbrVieP2; i++) {
            Coeur coeur = new Coeur();
            this.add(coeur).setLocation(1250 + 30 * i, 10);
            coeurP2.add(coeur);
        }

        nbrPointP1 = modele.getPointP1();
        nbrPointP2 = modele.getPointP2();
        lblP1Point.setText("Score : " + nbrPointP1);
        lblP2Point.setText("Score : " + nbrPointP2);

        invalidate();
        repaint();

        if (modele.getGagnant() == 1) {
            statistique();
        } else if (modele.getGagnant() == 2) {
            statistique();
        }
    }

    private void statistique() {

        stop = true;

        JLabel lblP1Fin = new JLabel("Joueur 1 : ");
        lblP1Fin.setForeground(Color.WHITE);
        lblP1Fin.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP1Fin.setLocation(400, 150);
        this.add(lblP1Fin).setSize(250, 40);

        JLabel lblP1FinPoints = new JLabel("Score : " + Integer.toString(nbrPointP1));
        lblP1FinPoints.setForeground(Color.WHITE);
        lblP1FinPoints.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP1FinPoints.setLocation(400, 200);
        this.add(lblP1FinPoints).setSize(250, 40);

        JLabel lblP1FinTir = new JLabel("Shot fired : " + Integer.toString(nbrTirP1));
        lblP1FinTir.setForeground(Color.WHITE);
        lblP1FinTir.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP1FinTir.setLocation(400, 250);
        this.add(lblP1FinTir).setSize(250, 40);

        System.out.println(nbrTirP1);
        System.out.println(nbrTirP1Hit*100);

        if (nbrTirP1 == 0) {
            p1HitPrc = 0;
        } else {
            p1HitPrc = ((nbrTirP1Hit*100) / nbrTirP1);
        }
                System.out.println(p1HitPrc);

        JLabel lblP1FinTirHit = new JLabel("Accuracy : " + Double.toString(p1HitPrc) + "%");
        lblP1FinTirHit.setForeground(Color.WHITE);
        lblP1FinTirHit.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP1FinTirHit.setLocation(400, 300);
        this.add(lblP1FinTirHit).setSize(350, 40);

        JLabel lblP2Fin = new JLabel("Joueur 2 : ");
        lblP2Fin.setForeground(Color.WHITE);
        lblP2Fin.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP2Fin.setLocation(850, 150);
        this.add(lblP2Fin).setSize(250, 40);

        JLabel lblP2FinPoints = new JLabel("Score : " + Integer.toString(nbrPointP2));
        lblP2FinPoints.setForeground(Color.WHITE);
        lblP2FinPoints.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP2FinPoints.setLocation(850, 200);
        this.add(lblP2FinPoints).setSize(250, 40);

        JLabel lblP2FinTir = new JLabel("Shot fired : " + Integer.toString(nbrTirP2));
        lblP2FinTir.setForeground(Color.WHITE);
        lblP2FinTir.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP2FinTir.setLocation(850, 250);
        this.add(lblP2FinTir).setSize(250, 40);

        if (nbrTirP2 == 0) {
            p2HitPrc = 0;
        } else {
            p2HitPrc = (nbrTirP2Hit / nbrTirP2) * 100;
        }
        JLabel lblP2FinTirHit = new JLabel("Accuracy : " + Double.toString(p2HitPrc) + "%");
        lblP2FinTirHit.setForeground(Color.WHITE);
        lblP2FinTirHit.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP2FinTirHit.setLocation(850, 300);
        this.add(lblP2FinTirHit).setSize(350, 40);

        modele.getListeNbrPointP1().add(nbrPointP1);
        modele.getListeNbrPointP2().add(nbrPointP2);
        modele.getListeNbrTirP1().add(nbrTirP1);
        modele.getListeNbrTirP2().add(nbrTirP2);
        modele.getListeP1HitPrc().add(p1HitPrc);
        modele.getListeP2HitPrc().add(p2HitPrc);

        int temp;

        double moyNbrPointP1 = 0;
        double moyNbrPointP2 = 0;
        double moyNbrTirP1 = 0;
        double moyNbrTirP2 = 0;
        double moyP1HitPrc = 0;
        double moyP2HitPrc = 0;

        for (double nbrPointP1 : modele.getListeNbrPointP1()) {
            moyNbrPointP1 += nbrPointP1;
        }
        moyNbrPointP1 = moyNbrPointP1 / modele.getListeNbrPointP1().size();

        for (double nbrPointP2 : modele.getListeNbrPointP2()) {
            moyNbrPointP2 += nbrPointP2;
        }
        moyNbrPointP2 = moyNbrPointP2 / modele.getListeNbrPointP2().size();

        for (double nbrTirP1 : modele.getListeNbrTirP1()) {
            moyNbrTirP1 += nbrTirP1;
        }
        moyNbrTirP1 = moyNbrTirP1 / modele.getListeNbrTirP1().size();

        for (double nbrTirP2 : modele.getListeNbrTirP2()) {
            moyNbrTirP2 += nbrTirP2;
        }
        moyNbrTirP2 = moyNbrTirP2 / modele.getListeNbrTirP2().size();

        for (Double p1HitPrc : modele.getListeP1HitPrc()) {
            moyP1HitPrc += p1HitPrc;
        }
        moyP1HitPrc = moyP1HitPrc / modele.getListeP1HitPrc().size();

        for (Double p2HitPrc :  modele.getListeP2HitPrc()) {
            moyP2HitPrc += p2HitPrc;
        }
        moyP2HitPrc = moyP2HitPrc /  modele.getListeP2HitPrc().size();

        JLabel lblP1FinPointsMoy = new JLabel("Avg Score : " + Double.toString(moyNbrPointP1));
        lblP1FinPointsMoy.setForeground(Color.WHITE);
        lblP1FinPointsMoy.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP1FinPointsMoy.setLocation(400, 350);
        this.add(lblP1FinPointsMoy).setSize(350, 60);

        JLabel lblP1FinTirMoy = new JLabel("Avg Shot fired : " + Double.toString(moyNbrTirP1));
        lblP1FinTirMoy.setForeground(Color.WHITE);
        lblP1FinTirMoy.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP1FinTirMoy.setLocation(400, 400);
        this.add(lblP1FinTirMoy).setSize(380, 60);

        JLabel lblP1FinTirHitMoy = new JLabel("Avg Accuracy : " + Double.toString(moyP1HitPrc) + "%");
        lblP1FinTirHitMoy.setForeground(Color.WHITE);
        lblP1FinTirHitMoy.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP1FinTirHitMoy.setLocation(400, 450);
        this.add(lblP1FinTirHitMoy).setSize(450, 60);

        JLabel lblP2FinPointsMoy = new JLabel("Avg Score : " + Double.toString(moyNbrPointP2));
        lblP2FinPointsMoy.setForeground(Color.WHITE);
        lblP2FinPointsMoy.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP2FinPointsMoy.setLocation(850, 350);
        this.add(lblP2FinPointsMoy).setSize(350, 60);

        JLabel lblP2FinTirMoy = new JLabel("Avg Shot fired : " + Double.toString(moyNbrTirP2));
        lblP2FinTirMoy.setForeground(Color.WHITE);
        lblP2FinTirMoy.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP2FinTirMoy.setLocation(850, 400);
        this.add(lblP2FinTirMoy).setSize(380, 60);

        JLabel lblP2FinTirHitMoy = new JLabel("Avg Accuracy : " + Double.toString(moyP2HitPrc) + "%");
        lblP2FinTirHitMoy.setForeground(Color.WHITE);
        lblP2FinTirHitMoy.setFont(new Font("ARCADE", Font.BOLD, 40));
        lblP2FinTirHitMoy.setLocation(850, 450);
        this.add(lblP2FinTirHitMoy).setSize(450, 60);
        
        JLabel lblGagnant = new JLabel("WINNER : JOUEUR "+modele.getGagnant());
        lblGagnant.setForeground(Color.WHITE);
        lblGagnant.setFont(new Font("ARCADE", Font.BOLD, 80));
        lblGagnant.setLocation(400, 500);
        this.add(lblGagnant).setSize(800, 80);
    }
}
