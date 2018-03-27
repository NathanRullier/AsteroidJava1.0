/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info203.TP2.Modele;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author 1529311
 */
public class Modele extends Observable {

    private int nbrVieP1 = 3;
    private int nbrVieP2 = 3;
    private int pointP1 = 0;
    private int pointP2 = 0;
    private int gagnant=0;
    private ArrayList<Integer> listeNbrPointP1 = new ArrayList<>();
    private ArrayList<Integer> listeNbrPointP2 = new ArrayList<>();
    private ArrayList<Integer> listeNbrTirP1 = new ArrayList<>();
    private ArrayList<Integer> listeNbrTirP2 = new ArrayList<>();
    private ArrayList<Double> listeP1HitPrc = new ArrayList<>();
    private ArrayList<Double> listeP2HitPrc = new ArrayList<>();
    
    public void majObserver() {
        setChanged();
        notifyObservers();     
    }

    
    public void sp1Hit() {
        nbrVieP1--;
        if(nbrVieP1==0){
         gagnant=2;
        }
        majObserver();
    }

    public void sp2Hit() {
       nbrVieP2--;
       if(nbrVieP2==0){
         gagnant=1;
        }
       majObserver();
    }

    public int getNbrVieP1() {
        return nbrVieP1;
    }

    public int getNbrVieP2() {
        return nbrVieP2;
    }

    public void sp1Point(int i) {
        pointP1= pointP1+i;
         majObserver();
    }
    public void sp2Point(int i) {
        pointP2= pointP2+i;
         majObserver();
    }

    public int getPointP1() {
        return pointP1;
    }

    public int getPointP2() {
        return pointP2;
    }

    public int getGagnant() {
        return gagnant;
    }

    public ArrayList<Integer> getListeNbrPointP1() {
        return listeNbrPointP1;
    }

    public ArrayList<Integer> getListeNbrPointP2() {
        return listeNbrPointP2;
    }

    public ArrayList<Integer> getListeNbrTirP1() {
        return listeNbrTirP1;
    }

    public ArrayList<Integer> getListeNbrTirP2() {
        return listeNbrTirP2;
    }

    public ArrayList<Double> getListeP1HitPrc() {
        return listeP1HitPrc;
    }

    public ArrayList<Double> getListeP2HitPrc() {
        return listeP2HitPrc;
    }
    
    
}
