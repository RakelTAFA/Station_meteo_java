/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.pkg201.pkg4.evenement;

import sae.pkg201.pkg4.connection.ConnectionSGBD;
import sae.pkg201.pkg4.Affichage.MaFenetre;

/**
 *Classe permettant l'acquisiton de donnée toutes les 10 secondes
 * @author kilia
 */
public class TimeEvent extends Thread {
    long timer;
    boolean running = true;
    MaFenetre mf;

    /**
     * Constructeur de la classe
     * @param timer temps entre chaque acquisition
     * @param mf instance MaFenetre
     */
    public TimeEvent(long timer,MaFenetre mf) {
        this.timer = timer;
        this.mf=mf;
    }
    /**
     * Permet l'arrêt de la fonction
     * @param running 
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    /**
     * Méthode override qui execute l'aquisition selon le timer
     */
    @Override
    public void run() {
        ConnectionSGBD rpi = ConnectionSGBD.getInstance("","");
        while (running) {
            rpi.addTemperature();
            rpi.getTemperature();
            mf.affichage();
            try {
                Thread.sleep(timer);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
