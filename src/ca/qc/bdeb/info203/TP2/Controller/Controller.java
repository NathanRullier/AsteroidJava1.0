/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.TP2.Controller;

import ca.qc.bdeb.info203.TP2.Modele.Modele;
import ca.qc.bdeb.info203.TP2.Vue.Fenetre;

/**
 *
 * @author 1529311
 */
public class Controller {
    private Modele modele = new Modele(); 
    private Fenetre fenetre = new Fenetre(this, modele);

    public void sp1Hit() {
        modele.sp1Hit();
    }

    public void sp2Hit() {
        modele.sp2Hit();
    }

    public void sp1Point(int i) {
    modele.sp1Point(i);
    }

    public void sp2Point(int i) {
        modele.sp2Point(i);
    }
    
}
