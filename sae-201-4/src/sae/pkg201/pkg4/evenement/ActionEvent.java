/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.pkg201.pkg4.evenement;

import sae.pkg201.pkg4.Affichage.MaFenetre;
import sae.pkg201.pkg4.connection.ConnectionSGBD;

/**
 *Classe permettant l'acquisition d'une donnée
 * @author kilia
 */
public class ActionEvent extends Thread {

    private static MaFenetre mf;

    /**
     * Méthode override permettant l'acquisition d'une donnée
     */
    @Override
    public void run() {
        ConnectionSGBD rpi = ConnectionSGBD.getInstance("", "");
        rpi.addTemperature();
        rpi.getTemperature();
        mf.affichage();
    }
    /**
     * Constructeur de la classe
     * @param mf instance MaFenetre
     */
    public ActionEvent(MaFenetre mf) {
        this.mf=mf;
    }

}
