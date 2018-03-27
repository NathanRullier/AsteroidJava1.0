/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.TP2.Vue;

import ca.qc.bdeb.info203.TP2.Controller.Controller;
import ca.qc.bdeb.info203.TP2.Modele.Modele;
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;

/**
 *
 * @author 1529311
 */
public class Fenetre extends JFrame {


    private ArrayList<Integer> listeKeyCodes = new ArrayList<>();
    private Controller controller;
    private Modele modele;
    private Monde monde;

    public Fenetre(Controller controller, Modele modele) {
        this.controller = controller;
        monde = new Monde(modele, controller);
        this.modele = modele;
        this.modele.addObserver(monde);
        creerEvenements();
        this.add(monde);

        this.setResizable(false);
        this.setSize(1500, 844);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    

    private void creerEvenements() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!listeKeyCodes.contains(e.getKeyCode())) {
                    listeKeyCodes.add(e.getKeyCode());
                    monde.setListeKeyCodes(listeKeyCodes);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                listeKeyCodes.remove(new Integer(e.getKeyCode()));
                monde.setListeKeyCodes(listeKeyCodes);
            }

        });
    }
}
