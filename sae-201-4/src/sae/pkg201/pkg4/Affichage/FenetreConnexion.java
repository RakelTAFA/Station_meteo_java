/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package sae.pkg201.pkg4.Affichage;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import sae.pkg201.pkg4.connection.ConnectionSGBD;
import sae.pkg201.pkg4.Affichage.MaFenetre;

/**
 * Classe qui crée la fenetre de connexion au SGBD
 * @author khali
 */
public class FenetreConnexion extends JFrame implements ActionListener{
    private JLabel title,id,mdp;
    private JTextField idTF;
    private JPasswordField mdpPF;
    private JButton buttonV;
    
    /**
     * Constructeur de la fenetre de connexion
     */
    public FenetreConnexion(){
        this.setTitle("Connexion");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 250);
        this.setLocation(200, 100);
        
        JPanel pan = new JPanel();
        pan.setLayout(new GridBagLayout());
        GridBagConstraints cont = new GridBagConstraints();
        cont.fill = GridBagConstraints.BOTH;
        
        this.id = new JLabel("Identifiant :");
        cont.gridx=0;
        cont.gridy=1;
        pan.add(this.id,cont);
        
        this.idTF = new JTextField();
        cont.gridx=0;
        cont.gridy=2;
        pan.add(this.idTF,cont);
        
        this.mdp = new JLabel("Mot de passe :");
        cont.gridx = 0;
        cont.gridy = 3;
        pan.add(this.mdp,cont);
        
        this.mdpPF = new JPasswordField();
        cont.gridx = 0;
        cont.gridy = 4;
        pan.add(this.mdpPF,cont);
        
        this.buttonV = new JButton("Validé");
        cont.gridx = 0;
        cont.gridy = 5;
        this.buttonV.addActionListener(this);
        pan.add(this.buttonV,cont);
        
        this.setContentPane(pan);
    }
    
    /**
     * Evenement lors du click de boutons.
     * @param e evenement effectué. 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.buttonV){
            String identifiant = this.idTF.getText();
            String motdp = this.mdpPF.getText();
            ConnectionSGBD rpi = ConnectionSGBD.getInstance(identifiant,motdp);
            int roleNb = 0;
            if(rpi.getInstancier() == true){
                this.setVisible(false);
                String role = rpi.request("SELECT role FROM all_user WHERE nom = "+"'"+identifiant+"'"+";");
                switch(role){
                    case "administrateur":
                        roleNb = 1;
                        MaFenetre win1 = new MaFenetre(roleNb,this);
                        win1.setVisible(true);
                        break;
                    case "super_utilisateur":
                        roleNb = 2;
                        MaFenetre win2 = new MaFenetre(roleNb,this);
                        win2.setVisible(true);
                        break;
                    case "simple_utilisateur":
                        roleNb = 3;
                        MaFenetre win3 = new MaFenetre(roleNb,this);
                        win3.setVisible(true);
                        break;
                }
                System.out.println(roleNb);
                this.setVisible(false);
            }
            else{
                JOptionPane.showMessageDialog(this,
                "Mot de passe incorect !",
                "Erreur de connection",
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Méthode qui retourne l'identifiant du JTextField
     * @return le texte du JTextField
     */
    public JTextField getIdTF() {
        return idTF;
    }
    /**
     * Méthode qui retourne le mot de passe du JPasswordField
     * @return le texte dans le JPasswordField
     */
    public JPasswordField getMdpPF() {
        return mdpPF;
    }
    
    
}
