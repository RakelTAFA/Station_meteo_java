/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.pkg201.pkg4.Données;

import sae.pkg201.pkg4.Données.DonneesUserBD;
import sae.pkg201.pkg4.Données.DonneesLigneBD;
import java.util.ArrayList;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 * Classe qui permet de gérer les données issues du RapsberryPi. Utile pour générer les graphiques par la suite.
 * @author rakel
 */

public final class Data {
    
    private static Data instance;
    private ArrayList<DonneesLigneBD> quadruplet;
    private ArrayList<DonneesUserBD> triplet;
    private ArrayList<DonneesLigneBD> quadrupletDefault;
    
    /**
     * Constructeur de la classe Data. Elle permet d'allouer l'espace mémoire aux 3 attributs quadruplet, triplet et quadrupletDefault. De plus, elle génère des données par défaut pour quadrupletDefault.
     */
    
    private Data(){
        quadruplet = new ArrayList<>();
        triplet = new ArrayList<>();
        quadrupletDefault=new ArrayList<>();
        generateQuadrupletDefault();
    }
    
    /**
     * Méthode qui permet d'obtenir l'instance de la classe si elle existe, ou sinon de la créer. C'est un singleton.
     * @return l'instance de la classe Data si elle existe déjà, sinon elle la créée.
     */
    
    public static Data getInstance(){
        if (instance == null){
            instance = new Data();
        }
        return instance;
    }
    
    /**
     * Méthode qui permet d'obtenir l'attribut quadrupletDefault.
     * @return quadrupletDefault
     */
    
    public ArrayList<DonneesLigneBD> getQuadrupletDefault(){
        return this.quadrupletDefault;
    }
    
    /**
     * Méthode qui génère les données par défaut pour l'attribut quadrupletDefault.
     */
    
    public void generateQuadrupletDefault(){
        ajouterElementQuadrupletDefault("16-06", "06:00:10", 17, 58);
        ajouterElementQuadrupletDefault("16-06", "18:40:00", 33, 40);
        ajouterElementQuadrupletDefault("17-06", "03:10:42", 20, 43);
        ajouterElementQuadrupletDefault("17-06", "10:59:59", 30, 50);
        ajouterElementQuadrupletDefault("18-06", "08:05:21", 23, 55);
        ajouterElementQuadrupletDefault("18-06", "23:48:00", 29, 55);
        ajouterElementQuadrupletDefault("19-06", "10:00:10", 27, 58);
        ajouterElementQuadrupletDefault("19-06", "19:36:01", 32, 57);
    }
    
    /**
     * Méthode qui renvoie l'attribut quadruplet.
     * @return quadruplet
     */
    
    public ArrayList<DonneesLigneBD> getQuadruplet(){
        return quadruplet;
    }
    
    /**
     * Méthode qui retourne l'attribut triplet.
     * @return triplet
     */
    
    public ArrayList<DonneesUserBD> getTriplet(){
        return this.triplet;
    }

    /**
     * Méthode qui permet de créer un objet de type DonneesLigneBD et de l'ajouter à quadruplet.
     * @param date
     * @param heure
     * @param temperature
     * @param humidite 
     */
    
    public void ajouterElementQuadruplet(String date, String heure, double temperature, double humidite){
        quadruplet.add(new DonneesLigneBD(date, heure, temperature, humidite));
    }
    
    /**
     * Méthode qui permet de créer un objet de type DonneesLigneBD et de l'ajouter à quadrupletDefault.
     * @param date
     * @param heure
     * @param temperature
     * @param humidite 
     */
    
    public void ajouterElementQuadrupletDefault(String date, String heure, double temperature, double humidite){
        quadrupletDefault.add(new DonneesLigneBD(date, heure, temperature, humidite));
    }
    
    /**
     * Permet de créer et de rajouter un objet de type DonneesUserBD dans triplet.
     * @param id
     * @param nom
     * @param role 
     */
    
    public void ajouterElementTriplet(int id,String nom, String role){
        triplet.add(new DonneesUserBD(id,nom,role));
    }
    
    // Permet de generer le dataset pour l'histogramme, il doit récuperer les valeurs dans quadruplet.

    /**
     * Permet de générer un dataset pour construire le graphique, à partir des valeurs contenues dans quadruplet. Construit le graphique de la température selon la date ou l'heure en fonction du paramètre.
     * @param enFonctionDe
     * @return dataset
     */
    
    public CategoryDataset generateDatasetLineGraph(String enFonctionDe){ 
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int taille = quadruplet.size();
        
        // Moyenne de temperature et humidite pour chaque jour
        if (enFonctionDe == "En fonction de la date"){
            String dateTest = quadruplet.get(0).getDate();
            int i = 0, compteur = 1;
            double sommeT;//, sommeH;
            
            // Cette boucle compte le nombre de dates différentes dans l'ensemble des lignes
            for (int j = 0; j < taille; j++){
                if (quadruplet.get(j).getDate() != dateTest){
                    compteur++;
                    dateTest = quadruplet.get(j).getDate();
                }
            }
            
            dateTest = quadruplet.get(0).getDate();
            int[] tab_nombre_par_date = new int[compteur];
            
            // Cette boucle compte le nombre de date pour chaque date et met dans le tableau ci-dessus
            int taille_tab = tab_nombre_par_date.length;
            for (int j = 0; j < taille_tab; j++){
                while (i < taille && dateTest == quadruplet.get(i).getDate()){
                    //tab_nombre_par_date[j]++;
                    i++;
                }
                tab_nombre_par_date[j] = i;
                if (i < taille && dateTest != quadruplet.get(i).getDate()){
                    dateTest = quadruplet.get(i).getDate();
                }
                
            }
            
            i = 0;
            
            // Cette boucle sert à calculer la moyenne de la température et de l'humidité sur un jour
            for (int k = 0; k < taille_tab; k++){
                sommeT = 0.0;//sommeH = 0.0;
                if (k == 0){
                    i = 0;
                }
                else{
                    i = tab_nombre_par_date[k - 1];
                }
                while (i < tab_nombre_par_date[k]){
                    sommeT += quadruplet.get(i).getTemperature();
                    //sommeH += quadruplet.get(i).getHumidite();
                    i++;
                }
                if (k != 0){
                    sommeT = sommeT / (i - tab_nombre_par_date[k - 1]);
                    //sommeH = sommeH / (i - tab_nombre_par_date[k - 1]);
                }
                else{
                    sommeT = sommeT / i;
                    //sommeH = sommeH / i;
                }
                if (k < taille_tab){
                    dataset.setValue(sommeT, "Courbe de la température en fonction de la date", quadruplet.get(i-1).getDate());
                }
                
            }
            
        }
        else if (enFonctionDe == "En fonction de l'heure"){
            String dateTest2 = quadruplet.get(0).getDate() + quadruplet.get(0).getHeure().substring(0, 2);
            //String dateTest2 = quadruplet.get(0).getDate() + " " + quadruplet.get(0).getHeure().substring(0, 2) + "h";
            int i2 = 0, compteur2 = 1;
            double sommeT2;
            
            // Cette boucle compte le nombre de dates-heures différentes dans l'ensemble des lignes
            for (int j = 0; j < taille; j++){
                if ((quadruplet.get(j).getDate() + quadruplet.get(j).getHeure().substring(0, 2)).compareTo(dateTest2) != 0){
                    compteur2++;
                    dateTest2 = quadruplet.get(j).getDate() + quadruplet.get(j).getHeure().substring(0, 2);
                }
            }
            
            dateTest2 = quadruplet.get(0).getDate() + quadruplet.get(0).getHeure().substring(0, 2);
            int[] tab_nombre_par_date2 = new int[compteur2];
            
            // Cette boucle compte le nombre de date pour chaque date et met dans le tableau ci-dessus
            int taille_tab2 = tab_nombre_par_date2.length;
            for (int j = 0; j < taille_tab2; j++){
                while (i2 < taille && (quadruplet.get(i2).getDate() + quadruplet.get(i2).getHeure().substring(0, 2)).compareTo(dateTest2) == 0){
                    //tab_nombre_par_date[j]++;
                    i2++;
                }
                tab_nombre_par_date2[j] = i2;
                if (i2 < taille && (quadruplet.get(i2).getDate() + quadruplet.get(i2).getHeure().substring(0, 2)).compareTo(dateTest2) != 0){
                    dateTest2 = quadruplet.get(i2).getDate() + quadruplet.get(i2).getHeure().substring(0, 2);
                }
                
            }
            
            i2 = 0;
            
            // Cette boucle sert à calculer la moyenne de la température et de l'humidité sur un jour
            for (int k = 0; k < taille_tab2; k++){
                sommeT2 = 0.0;
                if (k == 0){
                    i2 = 0;
                }
                else{
                    i2 = tab_nombre_par_date2[k - 1];
                }
                while (i2 < tab_nombre_par_date2[k]){
                    sommeT2 += quadruplet.get(i2).getTemperature();
                    i2++;
                }
                if (k != 0){
                    sommeT2 = sommeT2 / (i2 - tab_nombre_par_date2[k - 1]);
                }
                else{
                    sommeT2 = sommeT2 / i2;
                }
                if (k < taille_tab2){
                    dataset.setValue(sommeT2, "Courbe de la température en fonction de la date", quadruplet.get(i2-1).getDate() + " " + quadruplet.get(i2-1).getHeure().substring(0, 2) + "h");
                }
                
            }
            
        }
        
        return dataset;
    }
    
    /**
     * Permet de générer un dataset pour construire le graphique, à partir des valeurs contenues dans quadruplet. Construit le graphique de l'humidité selon la date ou l'heure en fonction du paramètre.
     * @param enFonctionDe
     * @return dataset
     */
    
    public CategoryDataset generateDatasetLineGraphHumidite(String enFonctionDe){ 
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int taille = quadruplet.size();
        
        // Moyenne de temperature et humidite pour chaque jour
        if (enFonctionDe == "En fonction de la date"){
            String dateTest = quadruplet.get(0).getDate();
            int i = 0, compteur = 1;
            double sommeH;
            
            // Cette boucle compte le nombre de dates différentes dans l'ensemble des lignes
            for (int j = 0; j < taille; j++){
                if (quadruplet.get(j).getDate() != dateTest){
                    compteur++;
                    dateTest = quadruplet.get(j).getDate();
                }
            }
            
            dateTest = quadruplet.get(0).getDate();
            int[] tab_nombre_par_date = new int[compteur];
            
            // Cette boucle compte le nombre de date pour chaque date et met dans le tableau ci-dessus
            int taille_tab = tab_nombre_par_date.length;
            for (int j = 0; j < taille_tab; j++){
                while (i < taille && dateTest == quadruplet.get(i).getDate()){
                    //tab_nombre_par_date[j]++;
                    i++;
                }
                tab_nombre_par_date[j] = i;
                if (i < taille && dateTest != quadruplet.get(i).getDate()){
                    dateTest = quadruplet.get(i).getDate();
                }
                
            }
            
            i = 0;
            
            // Cette boucle sert à calculer la moyenne de la température et de l'humidité sur un jour
            for (int k = 0; k < taille_tab; k++){
                sommeH = 0.0;//sommeH = 0.0;
                if (k == 0){
                    i = 0;
                }
                else{
                    i = tab_nombre_par_date[k - 1];
                }
                while (i < tab_nombre_par_date[k]){
                    sommeH += quadruplet.get(i).getHumidite();
                    //sommeH += quadruplet.get(i).getHumidite();
                    i++;
                }
                if (k != 0){
                    sommeH = sommeH / (i - tab_nombre_par_date[k - 1]);
                    //sommeH = sommeH / (i - tab_nombre_par_date[k - 1]);
                }
                else{
                    sommeH = sommeH / i;
                    //sommeH = sommeH / i;
                }
                if (k < taille_tab){
                    dataset.setValue(sommeH, "Courbe de l'humidité en fonction de la date", quadruplet.get(i-1).getDate());
                }
                
            }
            
        }
        else if (enFonctionDe == "En fonction de l'heure"){
            String dateTest2 = quadruplet.get(0).getDate() + quadruplet.get(0).getHeure().substring(0, 2);
            //String dateTest2 = quadruplet.get(0).getDate() + " " + quadruplet.get(0).getHeure().substring(0, 2) + "h";
            int i2 = 0, compteur2 = 1;
            double sommeH2;
            
            // Cette boucle compte le nombre de dates-heures différentes dans l'ensemble des lignes
            for (int j = 0; j < taille; j++){
                if ((quadruplet.get(j).getDate() + quadruplet.get(j).getHeure().substring(0, 2)).compareTo(dateTest2) != 0){
                    compteur2++;
                    dateTest2 = quadruplet.get(j).getDate() + quadruplet.get(j).getHeure().substring(0, 2);
                }
            }
            
            dateTest2 = quadruplet.get(0).getDate() + quadruplet.get(0).getHeure().substring(0, 2);
            int[] tab_nombre_par_date2 = new int[compteur2];
            
            // Cette boucle compte le nombre de date pour chaque date et met dans le tableau ci-dessus
            int taille_tab2 = tab_nombre_par_date2.length;
            for (int j = 0; j < taille_tab2; j++){
                while (i2 < taille && (quadruplet.get(i2).getDate() + quadruplet.get(i2).getHeure().substring(0, 2)).compareTo(dateTest2) == 0){
                    //tab_nombre_par_date[j]++;
                    i2++;
                }
                tab_nombre_par_date2[j] = i2;
                if (i2 < taille && (quadruplet.get(i2).getDate() + quadruplet.get(i2).getHeure().substring(0, 2)).compareTo(dateTest2) != 0){
                    dateTest2 = quadruplet.get(i2).getDate() + quadruplet.get(i2).getHeure().substring(0, 2);
                }
                
            }
            
            i2 = 0;
            
            // Cette boucle sert à calculer la moyenne de la température et de l'humidité sur un jour
            for (int k = 0; k < taille_tab2; k++){
                sommeH2 = 0.0;
                if (k == 0){
                    i2 = 0;
                }
                else{
                    i2 = tab_nombre_par_date2[k - 1];
                }
                while (i2 < tab_nombre_par_date2[k]){
                    sommeH2 += quadruplet.get(i2).getHumidite();
                    i2++;
                }
                if (k != 0){
                    sommeH2 = sommeH2 / (i2 - tab_nombre_par_date2[k - 1]);
                }
                else{
                    sommeH2 = sommeH2 / i2;
                }
                if (k < taille_tab2){
                    dataset.setValue(sommeH2, "Courbe de l'humidité en fonction de la date", quadruplet.get(i2-1).getDate() + " " + quadruplet.get(i2-1).getHeure().substring(0, 2) + "h");
                }
                
            }
            
        }
        
        return dataset;
    }

    /**
     * Permet de générer un dataset des 20 dernières valeurs arrivées dans le quadruplet, pour afficher la température en fonction d'un moment précis.
     * @return dataset
     */
    
    public CategoryDataset generateDatasetLastValuesTemperature(){
        final int NOMBRE_DE_VALEUR = 13;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int taille = quadruplet.size();

        if (taille >= NOMBRE_DE_VALEUR){
            for (int i = taille - (NOMBRE_DE_VALEUR); i < taille; i++){
                dataset.setValue(quadruplet.get(i).getTemperature(), "Courbe de la temperature à un instant précis", quadruplet.get(i).getHeure().substring(3,8));
            }
        }
        else {
            for (int i = 0; i < taille; i++){
                dataset.setValue(quadruplet.get(i).getTemperature(), "Courbe de la temperature à un instant précis", quadruplet.get(i).getHeure().substring(3,8));
            }
        }

        return dataset;
    } 
    
    /**
     * Permet de générer un dataset des 20 dernières valeurs arrivées dans le quadruplet, pour afficher l'humidité en fonction d'un moment précis.
     * @return dataset
     */
    
    public CategoryDataset generateDatasetLastValuesHumidite() {
        final int NOMBRE_DE_VALEUR = 13;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int taille = quadruplet.size();

        if (taille >= NOMBRE_DE_VALEUR) {
            for (int i = taille - (NOMBRE_DE_VALEUR); i < taille; i++) {
                dataset.setValue(quadruplet.get(i).getHumidite(), "Courbe de l'humidité à un instant précis", quadruplet.get(i).getHeure().substring(3, 8));
            }
        } else {
            for (int i = 0; i < taille; i++) {
                dataset.setValue(quadruplet.get(i).getHumidite(), "Courbe de l'humidité à un instant précis", quadruplet.get(i).getHeure().substring(3, 8));
            }
        }

        return dataset;
    }

    /**
     * Permet de générer un dataset par défaut avec les valeurs de la température contenu dans quadrupletDefault.
     * @return dataset
     */
    
    public CategoryDataset generateDefaultDatasetTemperature() {
        int taille = quadrupletDefault.size();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < taille; i++) {
            dataset.setValue(quadrupletDefault.get(i).getTemperature(), "Courbe de la température à un moment précis", quadrupletDefault.get(i).getDate() + " " + quadrupletDefault.get(i).getHeure());
        }
        return dataset;
    }

    /**
     * Permet de générer un dataset par défaut avec les valeurs de l'humidité contenu dans quadrupletDefault.
     * @return dataset
     */
    
    public CategoryDataset generateDefaultDatasetHumidite() {
        int taille = quadrupletDefault.size();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < taille; i++) {
            dataset.setValue(quadrupletDefault.get(i).getHumidite(), "Courbe de l'humidité à un moment précis", quadrupletDefault.get(i).getDate() + " " + quadrupletDefault.get(i).getHeure());
        }
        return dataset;
    }
}
