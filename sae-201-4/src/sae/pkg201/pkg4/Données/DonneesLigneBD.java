/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.pkg201.pkg4.Données;

/**
 * Classe qui permet créer un objet qui correspond à une ligne dans la base de données
 * @author rakel
 */
public class DonneesLigneBD {
    
    private String date;
    private String heure;
    private double temperature;
    private double humidite;

    /**
     * Constructeur de la classe DonneesLigneBD uqi initialise les attributs date, heure, température et humidité.
     * @param date
     * @param heure
     * @param temperature
     * @param humidite 
     */
    
    public DonneesLigneBD(String date, String heure, double temperature, double humidite) {
        this.date = date;
        this.temperature = temperature;
        this.heure = heure;
        this.humidite = humidite;
    }

    /**
     * Constructeur par défaut qui initialise les attributs avec des valeurs par défaut
     */
    
    public DonneesLigneBD() {
        date = "00-00";
        temperature = 0.0;
        heure = "00:00:00";
        humidite = 0.0;
    }
    
    /**
     * Permet d'obtenir l'attribut date.
     * @return date
     */

    public String getDate() {
        return date;
    }
    
    /**
     * Permet de récupérer la température.
     * @return temperature
     */

    public double getTemperature() {
        return temperature;
    }
    
    /**
     * Permet de récupérer l'heure.
     * @return heure
     */

    public String getHeure() {
        return heure;
    }
    
    /**
     * Permet de récupérer l'humidité.
     * @return humidite
     */

    public double getHumidite() {
        return humidite;
    }
    
}
