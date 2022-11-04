/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.pkg201.pkg4.connection;

import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import sae.pkg201.pkg4.Données.Data;
import Sensor.AnalogI2CInput;
import Sensor.DHT22;

/**
 *Classe permettant la connexion au SGBD
 * @author kilia
 */
public final class ConnectionSGBD {

    private static ConnectionSGBD instance;
    private static boolean instancier = false;
    private Connection conn = null;
    private Statement stmt = null;
    Data data = Data.getInstance();

    /**
     * Constructeur privé de la classe
     * @param id id de connexion
     * @param mdp mot de passe de connexion
     */
    private ConnectionSGBD(String id, String mdp) {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost/sae201_204";
            Properties props = new Properties();
            props.setProperty("user", id);
            props.setProperty("password", mdp);
            conn = DriverManager.getConnection(url, props);
            stmt = conn.createStatement();
            System.out.println("connecté");
            ConnectionSGBD.instancier = true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Fonction permettant de créer ou récupérer l'instance de la classe
     * @param id id de connexion
     * @param mdp mot de passe de connexion
     * @return retourne l'instance
     */
    public static ConnectionSGBD getInstance(String id, String mdp) {
        if (instance == null | instancier == false) {
            instance = new ConnectionSGBD(id, mdp);
        }
        return instance;
    }

    /**
     * Permet de faire une requête simple a la sgbd
     * @param requete requête à faire
     * @return retourne un String avec le retour de la requête
     */
    public String request(String requete) {
        if (instance == null) {
            return "Instance not initialize";
        }
        String rtn = "";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(requete);
            while (rs.next()) {
                rtn += rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rtn;

    }

    /**
     * Fonction permettant de savoir si l'objet a été instancié
     * @return 
     */
    public boolean getInstancier() {
        return instancier;
    }

    /**
     * Fonction permettant l'acquisition et l'ajout dans la base de données d'une mesure (température et humidité) 
     */
    public void addTemperature() {
        try {
            AnalogI2CInput i2c = new AnalogI2CInput(0);
            DHT22 sensor = new DHT22(5);
            String val = "(" + sensor.getTemperatureAndHumidity() + ",\'" + LocalDate.now().toString().substring(5) + "\',\'" + LocalTime.now().toString().substring(0, 8) + "\')";
            System.out.println(val);
            stmt.executeUpdate("INSERT INTO temp_humid (temperature,humidité,date,heure) VALUES" + val);
        } catch (IOException | I2CFactory.UnsupportedBusNumberException | SQLException ex) {
            System.out.println(ex.getMessage());        }
    }

    /**
     * Permet d'envoyer les températures acquise à la classe data
     */
    public void getTemperature() {
        ResultSet rs;
        data.getQuadruplet().clear();
        try {
            rs = stmt.executeQuery("Select * from temp_humid");
            while (rs.next()) {
                data.ajouterElementQuadruplet(rs.getString(4), rs.getString(5), rs.getDouble(2), rs.getDouble(3));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Permet de déconnecter l'instance de la sgbd
     */
    public void closeInstance() {
        try {
            conn.close();
            instance = null;
            instancier = false;
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    /**
    *permet la récupération du statement de la connexion
    */
    public Statement getStmt() {
        return stmt;
    }

}
