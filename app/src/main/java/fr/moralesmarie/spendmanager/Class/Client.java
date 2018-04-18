package fr.moralesmarie.spendmanager.Class;

/**
 * Created by utilisateur on 17/04/2018.
 */

public class Client {
    private int Id_Client;
    private String Titre_Client;
    private String Nom_Client;
    private String Prenom_Client;
    private String Adresse_Client;
    private String Cp_Client;
    private String Ville_Client;
    private String Telephone_Client;
    private String Mail_Client;
    private String Rs_Client;

    public Client(int id_Client, String titre_Client, String nom_Client, String prenom_Client, String adresse_Client, String cp_Client, String ville_Client, String telephone_Client, String mail_Client, String rs_Client) {
        Id_Client = id_Client;
        Titre_Client = titre_Client;
        Nom_Client = nom_Client;
        Prenom_Client = prenom_Client;
        Adresse_Client = adresse_Client;
        Cp_Client = cp_Client;
        Ville_Client = ville_Client;
        Telephone_Client = telephone_Client;
        Mail_Client = mail_Client;
        Rs_Client = rs_Client;
    }

    public int getId_Client() {
        return Id_Client;
    }
    public void setId_Client(int id_Client) {
        Id_Client = id_Client;
    }
    public String getTitre_Client() {
        return Titre_Client;
    }
    public void setTitre_Client(String titre_Client) {
        Titre_Client = titre_Client;
    }
    public String getNom_Client() {
        return Nom_Client;
    }
    public void setNom_Client(String nom_Client) {
        Nom_Client = nom_Client;
    }
    public String getPrenom_Client() {
        return Prenom_Client;
    }
    public void setPrenom_Client(String prenom_Client) {
        Prenom_Client = prenom_Client;
    }
    public String getAdresse_Client() {
        return Adresse_Client;
    }
    public void setAdresse_Client(String adresse_Client) {
        Adresse_Client = adresse_Client;
    }
    public String getCp_Client() {
        return Cp_Client;
    }
    public void setCp_Client(String cp_Client) {
        Cp_Client = cp_Client;
    }
    public String getVille_Client() {
        return Ville_Client;
    }
    public void setVille_Client(String ville_Client) {
        Ville_Client = ville_Client;
    }
    public String getTelephone_Client() {
        return Telephone_Client;
    }
    public void setTelephone_Client(String telephone_Client) {
        Telephone_Client = telephone_Client;
    }
    public String getMail_Client() {
        return Mail_Client;
    }
    public void setMail_Client(String mail_Client) {
        Mail_Client = mail_Client;
    }
    public String getRs_Client() {
        return Rs_Client;
    }
    public void setRs_Client(String rs_Client) {
        Rs_Client = rs_Client;
    }
}
