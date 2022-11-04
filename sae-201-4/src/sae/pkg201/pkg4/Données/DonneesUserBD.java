/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.pkg201.pkg4.Données;

/**
 * Classe qui stock les données des utilisateurs
 * @author khali
 */
public class DonneesUserBD {
    int id;
    String nom,role;
    
    /**
     * Constructeur de la classe 
     * @param id numero identifiant l'utilisateur
     * @param nom le nom de l'utilisateur 
     * @param role le role de l'utilisateur
     */
    public DonneesUserBD(int id, String nom, String role) {
        this.id = id;
        this.nom = nom;
        this.role = role;
    }
    /**
     * retourne l'identifiant de l'utilisateur
     * @return l'identifiant de l'utilisateur
     */
    public int getId() {
        return id;
    }
    /**
     * Retourne le nom de l'utilisateur
     * @return le nom de l'utilisateur
     */
    public String getNom() {
        return nom;
    }
    /**
     * retourne le role de l'utilisateur
     * @return le role de l'utilisateur
     */
    public String getRole() {
        return role;
    }
    /**
     * Modifie l'id de l'utilisateur
     * @param id id qui remplacera l'ancien
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * modifie le nom de l'utilisateur
     * @param nom nom qui remplacera l'ancien nom de l'utilisateur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    /**
     * modifie le role de l'utilisateur
     * @param role role qui replacera l'ancien nom de l'utilisateur
     */
    public void setRole(String role) {
        this.role = role;
    }
    
}
