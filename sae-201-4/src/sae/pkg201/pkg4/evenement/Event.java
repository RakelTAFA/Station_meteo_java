/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.pkg201.pkg4.evenement;
import sae.pkg201.pkg4.evenement.TimeEvent;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sae.pkg201.pkg4.Affichage.MaFenetre;
import Sensor.AnalogI2CInput;
import Sensor.DHT22;


/**
 *Classe permettant la gestion des différents évènements
 * @author kilia
 */
public class Event {
    private static Event instance;
    private static long timer = 10000;
    private static boolean timerEventRunning=false;
    private static TimeEvent te;
    private static ActionEvent ae;
    private static MaFenetre mf;

    /**
     * Permet de récupérer l'instance MaFenetre
     * @param mf 
     */
    public static void setMf(MaFenetre mf) {
        Event.mf = mf;
        te=new TimeEvent(timer,mf);
        
    }
    
    /**
     * Démarre l'évènement permettant l'acquisition de données toutes les 10 secondes
     */
    public static void startTimeEvent(){
        te.start();
    }
    
    /**
     * Démarre l'évènement permettant l'acquisition d'une donnée
     */
     public static void startActionEvent(){
        ae= new ActionEvent(mf);
        ae.start();
    }

     /**
      * Permet de modifier la valeur d'arrêt du TimeEvent
      * @param timerEventRunning 
      */
    public static void setTimerEventRunning(boolean timerEventRunning) {
        te.setRunning(timerEventRunning);
    }
     

}
