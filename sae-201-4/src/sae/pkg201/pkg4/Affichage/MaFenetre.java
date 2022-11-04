/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae.pkg201.pkg4.Affichage;

import sae.pkg201.pkg4.Données.Data;
import sae.pkg201.pkg4.connection.ConnectionSGBD;
import sae.pkg201.pkg4.evenement.Event;
import static com.sun.tools.javac.main.Option.X;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.time.LocalTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import org.jfree.data.category.CategoryDataset;

import org.jfree.data.statistics.HistogramDataset;

/**
 * Classe MaFenetre qui gère la partie graphique. Elle hérite de JFrame et
 * implémente ActionListener.
 *
 * @author rakel
 */
public class MaFenetre extends JFrame implements ActionListener {

    // Affichage basique
    private JPanel panneau;
    private JMenuBar menuBar;

    private JMenu menuDiagramme;

    private JMenuItem diagrammeItem1;
    private JMenuItem diagrammeItem2;
    private JMenuItem diagrammeItem3;

    private JMenu menuUser;
    private JMenuItem listUser;
    private JMenuItem addUser;
    private JMenuItem changeRight;

    private JMenu menuDeconnexion;
    private JMenuItem deco;

    //-private JMenuItem diagrammeItem1;
    private JScrollPane jScrollPane;
    private JTable jTable;

    private JDialog dialogue;
    private JButton exportCSV;
    private JButton submit;
    private JTextField texte;

    JCheckBox parDefaut;
    boolean etatParDefaut;

    // Elements pour les graphiques
    private JFreeChart chart = null;
    private static JTable tableau;

    JFrame frame;
    JFrame frameBar;
    private JComboBox comboGraph;
    private JButton validationComboGraph;

    private JButton validAddUser;
    private JTextField fieldNom;
    private JComboBox roleCB;
    private JPasswordField mdp;

    private JTextField nomUserEdit;
    private JComboBox listDroitEdit;
    private JButton validEditUser;
    // Element diagramme barres
    //-private JFrame frameBar;
    // Elements courbes
    private boolean affichage = true;
    private int display = 1;

    private ButtonGroup groupeChoix;
    private ButtonGroup groupeChoixDefaut;
    private JRadioButton choixTemperature;
    private JRadioButton choixHumidite;
    private JRadioButton choixTemperatureDefaut;
    private JRadioButton choixHumiditeDefaut;
    JDialog dialogAdd;
    JDialog dialogChange;

    JButton boutonAddLigne;

    //JLabel derniere_valeure;
    // Vient de la classe Data
    private boolean valeurDefault = false;
    private Data data = Data.getInstance();
    int role;
    FenetreConnexion fc;

    /**
     * Constructeur de la classe MaFenetre qui permet de générer la fenêtre
     * principale.
     *
     * @param role numéro correspondant aux 3 roles différent
     * @param fc FenetreConnexion qui permet de récuperer les données de la Fenetre de connexion.
     */
    public MaFenetre(int role, sae.pkg201.pkg4.Affichage.FenetreConnexion fc) {

        Event.setMf(this);
        Event.setTimerEventRunning(true);
        Event.startTimeEvent();
        this.fc = fc;

        this.role = role;

        this.setTitle("SAE 201 - 204");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();

        // Onglet Diagramme
        menuDiagramme = new JMenu("Diagrammes");
        menuBar.add(menuDiagramme);

        diagrammeItem1 = new JMenuItem("Tableau");
        diagrammeItem1.addActionListener(this);
        menuDiagramme.add(diagrammeItem1);
        diagrammeItem3 = new JMenuItem("Graphique");
        diagrammeItem3.addActionListener(this);
        menuDiagramme.add(diagrammeItem3);

        // Pour test
//        data.ajouterElementQuadruplet("15-06", "19:10:00", 30, 10);
//        data.ajouterElementQuadruplet("15-06", "20:10:00", 0, 9);
//        data.ajouterElementQuadruplet("16-06", "01:00:00", 20, 20);
//        data.ajouterElementQuadruplet("16-06", "02:00:00", 10, 10);
//        data.ajouterElementQuadruplet("16-06", "03:00:00", 0, 19);
//        data.ajouterElementQuadruplet("17-06", "21:10:00", 25.7, 9.2);
//
//        data.ajouterElementQuadruplet("17-06", "21:10:00", 35, 9.2);
//
//        data.ajouterElementQuadruplet("17-06", "21:10:00", 35, 0);
        //
        switch (role) {
            case 1:
                initUserMenu();
                break;
            case 2:
                initUserMenu();
                break;
            case 3:
                break;
        }

        this.menuDeconnexion = new JMenu("Déconnexion");
        menuBar.add(this.menuDeconnexion);

        this.deco = new JMenuItem("Déconnecté");
        this.deco.addActionListener(this);
        this.menuDeconnexion.add(this.deco);

        this.setJMenuBar(menuBar);
        affichage();
    }

    /**
     * Permet l'affichage de l'écran
     */
    public void affichage() {
        if (!valeurDefault | display == 1) {
            switch (display) {
                case 1:
                    initialisationTab();
                    break;
                case 2:
                    initialisationGraphique();
                    break;
                case 3:
                    initialisationGraphiqueHumidite();
                    break;
            }
        } else {
            initialisationGraphiqueDefault(affichage);
        }

    }
    /**
     * Permet l'affichage du tableau
     */
    public void initialisationTab() {
        panneau = new JPanel();
        panneau.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        //ConnectionRPI.getInstance("", "").getTemperature();
        initTableau();

        jScrollPane = new JScrollPane(tableau);
        add(jScrollPane);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panneau.add(jScrollPane, gbc);
        boolean selected=false;
        if(parDefaut!=null){
            selected = parDefaut.isSelected();
        }
        parDefaut = new JCheckBox("Par Defaut", false);
        parDefaut.addActionListener(this);
        etatParDefaut = parDefaut.isSelected();
        parDefaut.setSelected(selected);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panneau.add(parDefaut, gbc);

        exportCSV = new JButton("Export CSV");
        exportCSV.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panneau.add(exportCSV, gbc);

        boutonAddLigne = new JButton("Ajouter Ligne");
        boutonAddLigne.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panneau.add(boutonAddLigne, gbc);

        this.setContentPane(panneau);
        this.pack();
    }

    /**
     * Permet de générer le tableau de données de la page principal, complété avec les valeurs de la base de données.
     */
    
    public void initTableau() {
        String donnees[][];
        if (!valeurDefault) {
            donnees = new String[data.getQuadruplet().size()][4];
            int taille = data.getQuadruplet().size();
            for (int i = 0; i < taille; i++) {
                donnees[i][0] = data.getQuadruplet().get(i).getDate();
                donnees[i][1] = data.getQuadruplet().get(i).getHeure();
                donnees[i][2] = "" + data.getQuadruplet().get(i).getTemperature();
                donnees[i][3] = "" + data.getQuadruplet().get(i).getHumidite();
            }
        } else {
            donnees = new String[data.getQuadrupletDefault().size()][4];
            int taille = data.getQuadrupletDefault().size();
            for (int i = 0; i < taille; i++) {
                donnees[i][0] = data.getQuadrupletDefault().get(i).getDate();
                donnees[i][1] = data.getQuadrupletDefault().get(i).getHeure();
                donnees[i][2] = "" + data.getQuadrupletDefault().get(i).getTemperature();
                donnees[i][3] = "" + data.getQuadrupletDefault().get(i).getHumidite();
            }
        }

        String ligneTitre[] = {"DATE", "HEURE", "TEMPERATURE", "HUMIDITE"};
        tableau = new JTable(donnees, ligneTitre);

        tableau.setPreferredScrollableViewportSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.6), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.55)));
    }

    /**
     * Permet de générer le tableau de données de la page des utilisateurs avec les valeurs de la table des utilisateurs.
     */
    
    public void initTabUser() {
        String donnees[][];

        donnees = new String[data.getTriplet().size()][3];
        int taille = data.getTriplet().size();
        for (int i = 0; i < taille; i++) {
            donnees[i][0] = Integer.toString(data.getTriplet().get(i).getId());
            donnees[i][1] = data.getTriplet().get(i).getNom();
            donnees[i][2] = "" + data.getTriplet().get(i).getRole();
        }

        String ligneTitre[] = {"ID", "NOM", "ROLE"};
        tableau = new JTable(donnees, ligneTitre);
    }
    
    /**
     * Permet de générer le graphique de la température, en fonction de la date ou de l'heure, ou les 20 dernières valeurs.
     */

    public void initialisationGraphique() {
        String str;
        if (comboGraph == null) {
            str = "En fonction de la date";
        } else {
            if (comboGraph.isFocusOwner()) {
                return;
            }
            str = comboGraph.getSelectedItem().toString();
        }

        comboGraph = new JComboBox();
        comboGraph.addItem("En fonction de la date");
        comboGraph.addItem("En fonction de l'heure");
        comboGraph.addItem("Dernières valeurs");
        comboGraph.setSelectedItem(str);

        validationComboGraph = new JButton("OK");
        validationComboGraph.addActionListener(this);

        groupeChoix = new ButtonGroup();

        choixTemperature = new JRadioButton();
        choixTemperature.setText("T");
        choixTemperature.setMnemonic(KeyEvent.VK_C);
        //
        choixTemperature.setSelected(true);
        //
        choixTemperature.setActionCommand("T");
        choixTemperature.addActionListener(this);

        choixHumidite = new JRadioButton();
        choixHumidite.setText("H");
        choixHumidite.setMnemonic(KeyEvent.VK_C);
        //
        choixHumidite.setSelected(false);
        //
        choixHumidite.setActionCommand("H");
        choixHumidite.addActionListener(this);

        if (str == "En fonction de l'heure") {
            chart = ChartFactory.createLineChart("Temperature selon l'heure", "Heures", "Temperature en degrées C", data.generateDatasetLineGraph(str));
        } else if (str == "En fonction de la date") {
            chart = ChartFactory.createLineChart("Temperature selon le jour", "Jours", "Temperature en degrées C", data.generateDatasetLineGraph(str));
        } else if (str == "Dernières valeurs") {
            chart = ChartFactory.createLineChart("Temperature à un instant donné", "Dernière mesure à " + LocalTime.now().toString().substring(0, 2) + "h (en min:sec)", "Temperature en degrée C", data.generateDatasetLastValuesTemperature());
        }

        ChartPanel panneauChartLine = new ChartPanel(chart);
        panneauChartLine.setLayout(null);
        comboGraph.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.02), 4, (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.15), 25);
        validationComboGraph.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.17), 4, 60, 25);
        choixTemperature.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.77), 5, (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.05), 20);
        choixHumidite.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.83), 5, (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.05), 20);

        groupeChoix.add(choixTemperature);
        groupeChoix.add(choixHumidite);

        panneauChartLine.add(comboGraph);
        panneauChartLine.add(validationComboGraph);
        panneauChartLine.add(choixTemperature);
        panneauChartLine.add(choixHumidite);

        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.BLUE);
        p.setBackgroundPaint(new Color(235, 235, 235));
//        frame.setContentPane(panneauChartLine);
//        frame.setVisible(true);
        this.setContentPane(panneauChartLine);
        this.invalidate();
        this.validate();
        this.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.90));
    }
    
    /**
     *  Permet de générer le graphique de l'humidité, en fonction de la date ou de l'heure, ou les 20 dernières valeurs.
     */

    public void initialisationGraphiqueHumidite() {
//        if (frame == null){
//            frame = new JFrame("Courbes");
//        }
//    
//        frame.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.49), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.50));
//        frame.setResizable(false);

        String str;
        if (comboGraph == null) {
            str = "En fonction de la date";
        } else {
            if (comboGraph.isFocusOwner()) {
                return;
            }
            str = comboGraph.getSelectedItem().toString();
        }
        comboGraph = new JComboBox();
        comboGraph.addItem("En fonction de la date");
        comboGraph.addItem("En fonction de l'heure");
        comboGraph.addItem("Dernières valeurs");
        comboGraph.setSelectedItem(str);

        validationComboGraph = new JButton("OK");
        validationComboGraph.addActionListener(this);

        if (groupeChoix == null) {
            groupeChoix = new ButtonGroup();
        }
        if (choixTemperature == null) {
            choixTemperature = new JRadioButton();
            choixTemperature.setText("T");
            choixTemperature.setMnemonic(KeyEvent.VK_C);
            //
            choixTemperature.setSelected(true);
            //
            choixTemperature.setActionCommand("T");
            choixTemperature.addActionListener(this);
        }

        if (choixHumidite == null) {
            choixHumidite = new JRadioButton();
            choixHumidite.setText("H");
            choixHumidite.setMnemonic(KeyEvent.VK_C);
            //
            choixHumidite.setSelected(false);
            //
            choixHumidite.setActionCommand("H");
            choixHumidite.addActionListener(this);
        }

        if (str == "En fonction de l'heure") {
            chart = ChartFactory.createLineChart("Humidité selon l'heure", "Heures", "Humidite en pourcentage C", data.generateDatasetLineGraphHumidite(str));
        } else if (str == "En fonction de la date") {
            chart = ChartFactory.createLineChart("Humidité selon le jour", "Jours", "Humidité en pourcentage", data.generateDatasetLineGraphHumidite(str));
        } else if (str == "Dernières valeurs") {
            chart = ChartFactory.createLineChart("Humidité à un instant donné", "Dernière mesure à " + LocalTime.now().toString().substring(0, 2) + "h (en min:sec)", "Humidité en pourcentage", data.generateDatasetLastValuesHumidite());
        }

        ChartPanel panneauChartLine = new ChartPanel(chart);
        panneauChartLine.setLayout(null);
        comboGraph.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.02), 4, (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.15), 25);
        validationComboGraph.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.17), 4, 60, 25);
        choixTemperature.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.77), 5, (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.05), 20);
        choixHumidite.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.83), 5, (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.05), 20);

        groupeChoix.add(choixTemperature);
        groupeChoix.add(choixHumidite);

        //JLabel derniere_valeure = new JLabel();
        panneauChartLine.add(comboGraph);
        panneauChartLine.add(validationComboGraph);
        panneauChartLine.add(choixTemperature);
        panneauChartLine.add(choixHumidite);

        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.BLUE);
        p.setBackgroundPaint(new Color(235, 235, 235));
//        frame.setContentPane(panneauChartLine);
//        frame.setVisible(true);  
        this.setContentPane(panneauChartLine);
        this.invalidate();
        this.validate();
        this.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.90));
    }
    
    /**
     * Permet d'ouvrir la boîte de dialogue utile pour rentrer le nom du document à générer
     */

    public void openExportDialogBox() {
        dialogue = new JDialog();
        dialogue.setTitle("Exporter vers");
        JPanel panneauDialogue = new JPanel();
        panneauDialogue.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel label = new JLabel("Nom du fichier : ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        panneauDialogue.add(label, gbc);

        texte = new JTextField("Entrez un nom de fichier");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panneauDialogue.add(texte, gbc);

        submit = new JButton("Export");
        submit.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        panneauDialogue.add(submit, gbc);

        dialogue.setSize(400, 120);
        dialogue.setModal(true);
        dialogue.setResizable(false);
        dialogue.setContentPane(panneauDialogue);
        dialogue.setVisible(true);
    }
    
    /**
     * Permet de générer le document .csv pour sauvegarder les données contenues dans la base.
     * @param nom_fichier 
     */

    public void exportCurrentData(String nom_fichier) {
        File file = new File(nom_fichier + ".csv");
        try {
            FileWriter fw = new FileWriter(file, true);

            for (int i = 0; i < 4; i++) {
                fw.write(tableau.getColumnName(i));
                fw.flush();
                fw.write(";");
                fw.flush();
            }
            fw.write("\n");
            fw.flush();

            int taille = data.getQuadruplet().size();
            for (int i = 0; i < taille; i++) {
                fw.write(tableau.getValueAt(i, 0).toString());
                fw.flush();
                fw.write(";");
                fw.flush();
                fw.write(tableau.getValueAt(i, 1).toString());
                fw.flush();
                fw.write(";");
                fw.flush();
                fw.write(tableau.getValueAt(i, 2).toString());
                fw.flush();
                fw.write(";");
                fw.flush();
                fw.write(tableau.getValueAt(i, 3).toString());
                fw.flush();
                fw.write(";");
                fw.flush();
                fw.write("\n");
                fw.flush();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Initialise le JDialog pour ajouter des utilisateur.
     */
    
    public void initDialogAddUser() {
        dialogAdd = new JDialog(this, "Ajouter utilisateur", true);
        JPanel pan = new JPanel();
        pan.setLayout(new GridBagLayout());
        GridBagConstraints cont = new GridBagConstraints();
        cont.fill = GridBagConstraints.BOTH;

        dialogAdd.setSize(300, 300);

        JLabel nomUser = new JLabel("Nom :");
        cont.gridx = 0;
        cont.gridy = 0;
        pan.add(nomUser, cont);

        fieldNom = new JTextField();
        fieldNom.setPreferredSize(new Dimension(100, 20));
        cont.gridx = 0;
        cont.gridy = 1;
        pan.add(fieldNom, cont);

        JLabel nomRole = new JLabel("Role : ");
        cont.gridx = 0;
        cont.gridy = 4;
        pan.add(nomRole, cont);

        String[] s1 = {"administrateur", "super_utilisateur", "simple_utilisateur"};
        String[] s2 = {"super_utilisateur", "simple_utilisateur"};
        if (this.role == 1) {
            this.roleCB = new JComboBox(s1);
        } else if (this.role == 2) {
            this.roleCB = new JComboBox(s2);
        }
        cont.gridx = 0;
        cont.gridy = 5;
        pan.add(roleCB, cont);

        JLabel labelMdp = new JLabel("Mot de passe : ");
        cont.gridx = 0;
        cont.gridy = 2;
        pan.add(labelMdp, cont);

        this.mdp = new JPasswordField();
        cont.gridx = 0;
        cont.gridy = 3;
        pan.add(this.mdp, cont);

        validAddUser = new JButton("Validé");
        cont.gridx = 0;
        cont.gridy = 6;
        validAddUser.addActionListener(this);
        pan.add(validAddUser, cont);
        pan.add(validAddUser, cont);

        dialogAdd.setContentPane(pan);
        dialogAdd.setVisible(true);
    }
    
    /**
     * Initialise le JMenu des utilisateurs
     */

    private void initUserMenu() {
        this.menuUser = new JMenu("Utilisateurs");
        menuBar.add(this.menuUser);

        this.listUser = new JMenuItem("Liste utilisateurs");
        this.listUser.addActionListener(this);
        this.menuUser.add(this.listUser);

        this.addUser = new JMenuItem("Ajouter utilisateur");
        this.addUser.addActionListener(this);
        this.menuUser.add(this.addUser);

        this.changeRight = new JMenuItem("Modifier permissions");
        this.changeRight.addActionListener(this);
        this.menuUser.add(this.changeRight);
    }
    
    /**
     * Action effectué lorsque l'utilisateur valide l'ajout d'un utilisateur.
     */

    public void initValidAddUserButton() {
        String nom = this.fieldNom.getText();
        String mdp = this.mdp.getText();
        String role = (String) this.roleCB.getSelectedItem();
        boolean check = false;

        String usrId = this.fc.getIdTF().getText();
        String usrMdp = this.fc.getMdpPF().getText();
        ConnectionSGBD rpi = ConnectionSGBD.getInstance(usrId, usrMdp);
        try {
            rpi.getStmt().executeUpdate("CREATE USER " + nom + ";");
            rpi.getStmt().executeUpdate("ALTER USER " + nom + " WITH PASSWORD '" + mdp + "';");
            rpi.getStmt().executeUpdate("GRANT " + role + " TO " + nom);
            switch (role) {
                case "administrateur":
                    rpi.getStmt().executeUpdate("ALTER USER " + nom + " WITH CREATEDB CREATEROLE INHERIT LOGIN REPLICATION BYPASSRLS;");
                    rpi.getStmt().executeUpdate("GRANT ALL ON ALL TABLES IN SCHEMA public TO " + nom + ";");
                    rpi.getStmt().executeUpdate("GRANT ALL ON DATABASE sae201_204 TO " + nom + ";");
                    rpi.getStmt().executeUpdate("GRANT ALL ON DATABASE postgres TO " + nom + ";");
                    rpi.getStmt().executeUpdate("GRANT USAGE, SELECT ON SEQUENCE temp_humid_id_seq TO " + nom + ";");
                    rpi.getStmt().executeUpdate("GRANT USAGE, SELECT ON SEQUENCE all_user_id_seq TO " + nom + ";");
                    check = true;
                    break;
                case "super_utilisateur":
                    rpi.getStmt().executeUpdate("ALTER USER " + nom + " WITH CREATEROLE LOGIN;");
                    rpi.getStmt().executeUpdate("GRANT ALL ON ALL TABLES IN SCHEMA public TO " + nom + ";");
                    rpi.getStmt().executeUpdate("GRANT ALL ON DATABASE sae201_204 TO " + nom + ";");
                    rpi.getStmt().executeUpdate("GRANT USAGE, SELECT ON SEQUENCE temp_humid_id_seq TO " + nom + ";");
                    rpi.getStmt().executeUpdate("GRANT USAGE, SELECT ON SEQUENCE all_user_id_seq TO " + nom + ";");
                    check = true;
                    break;
                case "simple_utilisateur":
                    rpi.getStmt().executeUpdate("ALTER USER " + role + " WITH LOGIN;");
                    rpi.getStmt().executeUpdate("GRANT SELECT,INSERT,UPDATE,DELETE ON temp_humid TO " + role + ";");
                    rpi.getStmt().executeUpdate("GRANT CONNECT ON DATABASE sae201_204 TO " + role + ";");
                    rpi.getStmt().executeUpdate("GRANT USAGE, SELECT ON SEQUENCE temp_humid_id_seq TO " + role + ";");
                    rpi.getStmt().executeUpdate("GRANT USAGE, SELECT ON SEQUENCE all_user_id_seq TO " + role + ";");
                    rpi.getStmt().executeUpdate("GRANT SELECT ON all_user TO " + role + ";");
                    check = true;
                    break;
            }
            if (check) {
                rpi.getStmt().executeUpdate("INSERT INTO all_user (nom,role) VALUES ('" + nom + "','" + role + "');");
                JOptionPane.showMessageDialog(this,
                        "Utilisateur ajouter",
                        "Votre utilisateur à bien été crée !",
                        JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MaFenetre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Initialise la JDialog pour changer les droits d'un utilisateur.
     */

    public void initChangeRightWin() {
        dialogChange = new JDialog(this, "Modifier Droits", true);
        JPanel pan = new JPanel();
        pan.setLayout(new GridBagLayout());
        GridBagConstraints cont = new GridBagConstraints();
        cont.fill = GridBagConstraints.BOTH;
        dialogChange.setSize(300, 300);

        JLabel nomUser = new JLabel("Utilisateur à modifier :");
        cont.gridx = 0;
        cont.gridy = 0;
        pan.add(nomUser, cont);

        this.nomUserEdit = new JTextField();
        cont.gridx = 0;
        cont.gridy = 1;
        pan.add(this.nomUserEdit, cont);

        String[] s1 = {"administrateur", "super_utilisateur", "simple_utilisateur"};
        String[] s2 = {"super_utilisateur", "simple_utilisateur"};
        if (this.role == 1) {
            this.listDroitEdit = new JComboBox(s1);
        } else if (this.role == 2) {
            this.listDroitEdit = new JComboBox(s2);
        }
        cont.gridx = 0;
        cont.gridy = 2;
        pan.add(this.listDroitEdit, cont);

        this.validEditUser = new JButton("Valider");
        cont.gridx = 0;
        cont.gridy = 3;
        pan.add(this.validEditUser, cont);
        validEditUser.addActionListener(this);

        dialogChange.setContentPane(pan);
        dialogChange.setVisible(true);
    }
    
    /**
     * Permet de générer le graphique de la température ou de l'humidité en fonction des moments précis.
     * @param choix 
     */

    public void initialisationGraphiqueDefault(boolean choix) {
        if (choix) {
            chart = ChartFactory.createLineChart("Temperature à un instant donné", "Moment", "Température en degrée C", data.generateDefaultDatasetTemperature());
        } else {
            chart = ChartFactory.createLineChart("Humidité à un instant donné", "Moment", "Humidité en pourcentage", data.generateDefaultDatasetHumidite());
        }
        ChartPanel panneauChartLine = new ChartPanel(chart);
        panneauChartLine.setLayout(null);
        
        if (groupeChoixDefaut == null) {
            groupeChoixDefaut = new ButtonGroup();
        }
        boolean selected=true;
        if(choixTemperatureDefaut!=null){
            selected=choixTemperatureDefaut.isSelected();
        }
        choixTemperatureDefaut = new JRadioButton();
        choixTemperatureDefaut.setText("T");
        choixTemperatureDefaut.setMnemonic(KeyEvent.VK_C);
        choixTemperatureDefaut.setSelected(selected);
        choixTemperatureDefaut.setActionCommand("T");
        choixTemperatureDefaut.addActionListener(this);

        choixHumiditeDefaut = new JRadioButton();
        choixHumiditeDefaut.setText("H");
        choixHumiditeDefaut.setMnemonic(KeyEvent.VK_C);
        choixHumiditeDefaut.setSelected(!selected);
        choixHumiditeDefaut.setActionCommand("H");
        choixHumiditeDefaut.addActionListener(this);
        
        choixTemperatureDefaut.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.77), 5, (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.05), 20);
        choixHumiditeDefaut.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.83), 5, (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.05), 20);

        
        groupeChoixDefaut.add(choixTemperatureDefaut);
        groupeChoixDefaut.add(choixHumiditeDefaut);
        
        panneauChartLine.add(choixTemperatureDefaut);
        panneauChartLine.add(choixHumiditeDefaut);
        
        this.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.90));
        this.setContentPane(panneauChartLine);
        this.setVisible(true);
    }

    
    /**
     * Liste des actions a effectuer selon le bouton cliqué.
     * @param e Action effectuée.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == diagrammeItem1) {
            display = 1;
            affichage();
        }
        if (e.getSource() == diagrammeItem3) {
            display = 2;
            affichage();
        }
        if (e.getSource() == exportCSV) {
            openExportDialogBox();
        }
        if (e.getSource() == boutonAddLigne) {
            Event.startActionEvent();
        }
        if (e.getSource() == submit) {
            exportCurrentData(texte.getText());
            dialogue.dispose();
        }
        if (e.getSource() == validationComboGraph) {
            if (affichage == true) {
                display = 2;
                affichage();
            } else {
                display = 3;
                affichage();
            }
        }
        if (e.getSource() == this.deco) {
            Event.setTimerEventRunning(false);
            this.setVisible(false);
            this.fc.setVisible(true);
            ConnectionSGBD.getInstance("", "").closeInstance();
        }
        if (e.getSource() == this.listUser) {
            JDialog d = new JDialog(this, "Liste des utilisateurs", true);
            JPanel pan = new JPanel();
            pan.setLayout(new GridBagLayout());
            GridBagConstraints cont = new GridBagConstraints();
            cont.fill = GridBagConstraints.BOTH;

            d.setSize(500, 500);

            String usrId = this.fc.getIdTF().getText();
            String usrMdp = this.fc.getMdpPF().getText();
            ConnectionSGBD rpi = ConnectionSGBD.getInstance(usrId, usrMdp);
            int i = 0;
            int[] s1 = new int[50];
            String[] s2 = new String[50];
            String[] s3 = new String[50];
            try {
                ResultSet rs1 = rpi.getStmt().executeQuery("SELECT id FROM all_user;");
                while (rs1.next()) {
                    s1[i] = rs1.getInt(1);
                    i++;
                }
                i = 0;
                ResultSet rs2 = rpi.getStmt().executeQuery("SELECT nom FROM all_user;");
                while (rs2.next()) {
                    s2[i] = rs2.getString(1);
                    i++;
                }
                i = 0;
                ResultSet rs3 = rpi.getStmt().executeQuery("SELECT role FROM all_user;");
                while (rs3.next()) {
                    s3[i] = rs3.getString(1);
                    i++;
                }
                for (int j = 0; j < s1.length; j++) {
                    if (s1[j] != 0) {
                        this.data.ajouterElementTriplet(s1[j], s2[j], s3[j]);
                    }

                }
                initTabUser();
            } catch (SQLException ex) {
                Logger.getLogger(MaFenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
            jScrollPane = new JScrollPane(tableau);
            add(jScrollPane);
            cont.gridx = 0;
            cont.gridy = 0;
            cont.gridwidth = 4;
            pan.add(jScrollPane, cont);
            d.setContentPane(pan);
            d.setVisible(true);
        }
        if (e.getSource() == this.addUser) {
            initDialogAddUser();
        }
        if (e.getSource() == this.changeRight) {

        }
        if (e.getSource() == this.changeRight) {
            initChangeRightWin();
        }
        if (e.getSource() == this.validAddUser) {
            initValidAddUserButton();
            dialogAdd.dispose();
        }
        if (e.getSource() == this.validEditUser) {
            String nom = this.nomUserEdit.getText();
            String role = (String) this.listDroitEdit.getSelectedItem();
            String actualRole = "";
            boolean check = false;
            String usrId = this.fc.getIdTF().getText();
            String usrMdp = this.fc.getMdpPF().getText();
            ConnectionSGBD rpi = ConnectionSGBD.getInstance(usrId, usrMdp);
            try {
                rpi.getStmt().executeUpdate("REVOKE ALL ON ALL TABLES IN SCHEMA public FROM " + nom + ";");
                rpi.getStmt().executeUpdate("REVOKE ALL ON ALL SEQUENCES IN SCHEMA public FROM " + nom + ";");
                rpi.getStmt().executeUpdate("REVOKE ALL ON DATABASE sae201_204 FROM " + nom + ";");
                rpi.getStmt().executeUpdate("REVOKE ALL ON DATABASE postgres FROM " + nom + ";");
                rpi.getStmt().executeUpdate("REVOKE ALL ON SCHEMA public FROM " + nom + ";");
                switch (role) {
                    case "administrateur":
                        rpi.getStmt().executeUpdate("ALTER USER " + nom + " WITH CREATEDB CREATEROLE INHERIT LOGIN REPLICATION BYPASSRLS;");
                        rpi.getStmt().executeUpdate("GRANT ALL ON ALL TABLES IN SCHEMA public TO " + nom + ";");
                        rpi.getStmt().executeUpdate("GRANT ALL ON DATABASE sae201_204 TO " + nom + ";");
                        rpi.getStmt().executeUpdate("GRANT ALL ON DATABASE postgres TO " + nom + ";");
                        rpi.getStmt().executeUpdate("GRANT USAGE, SELECT ON SEQUENCE temp_humid_id_seq TO " + nom + ";");
                        rpi.getStmt().executeUpdate("GRANT USAGE, SELECT ON SEQUENCE all_user_id_seq TO " + nom + ";");
                        check = true;
                        break;
                    case "super_utilisateur":
                        rpi.getStmt().executeUpdate("ALTER USER " + nom + " WITH CREATEROLE LOGIN;");
                        rpi.getStmt().executeUpdate("GRANT ALL ON ALL TABLES IN SCHEMA public TO " + nom + ";");
                        rpi.getStmt().executeUpdate("GRANT ALL ON DATABASE sae201_204 TO " + nom + ";");
                        rpi.getStmt().executeUpdate("GRANT USAGE, SELECT ON SEQUENCE temp_humid_id_seq TO " + nom + ";");
                        rpi.getStmt().executeUpdate("GRANT USAGE, SELECT ON SEQUENCE all_user_id_seq TO " + nom + ";");
                        check = true;
                        break;
                    case "simple_utilisateur":
                        rpi.getStmt().executeUpdate("ALTER USER " + role + " WITH LOGIN;");
                        rpi.getStmt().executeUpdate("GRANT SELECT,INSERT,UPDATE,DELETE ON temp_humid TO " + role + ";");
                        rpi.getStmt().executeUpdate("GRANT CONNECT ON DATABASE sae201_204 TO " + role + ";");
                        rpi.getStmt().executeUpdate("GRANT USAGE, SELECT ON SEQUENCE temp_humid_id_seq TO " + role + ";");
                        rpi.getStmt().executeUpdate("GRANT USAGE, SELECT ON SEQUENCE all_user_id_seq TO " + role + ";");
                        rpi.getStmt().executeUpdate("GRANT SELECT ON all_user TO " + role + ";");
                        check = true;
                        break;
                }
                if (check) {
                    rpi.getStmt().executeUpdate("UPDATE all_user SET role = '" + role + "' WHERE nom = '" + nom + "';");
                    JOptionPane.showMessageDialog(this,
                            "Utilisateur modifié",
                            "Les droits pour l'utilisateur " + nom + " ont été correctement modifiés",
                            JOptionPane.INFORMATION_MESSAGE);
                    dialogChange.dispose();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MaFenetre.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        if (e.getSource() == choixTemperature) {
            affichage = true;

        }
        if (e.getSource() == choixHumidite) {
            affichage = false;


        }
        if (e.getSource() == choixTemperatureDefaut) {
            affichage = true;
            affichage();
        }
        if (e.getSource() == choixHumiditeDefaut) {
            affichage = false;
            affichage();

        }
        if (e.getSource() == parDefaut) {
            etatParDefaut = parDefaut.isSelected();
            if (etatParDefaut == false) {
                
                valeurDefault = false;
                affichage();
            } else {
                
                valeurDefault = true;
                affichage();
            }
        }
    }
}
