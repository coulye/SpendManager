package fr.moralesmarie.spendmanager.Class;

import java.util.ArrayList;

/**
 * Created by utilisateur on 20/04/2018.
 */

public class Notefrais {
    private int Id_Notefrais;
    private String Date_Notefrais;
    private String DateSoumission_Notefrais;
    private int Id_Utilisateur;
    private int Id_Client;
    private ArrayList<Depense> lesDepenses;

    public Notefrais(int Id_Notefrais, String Date_Notefrais, String DateSoumission_Notefrais, int Id_Utilisateur, int Id_Client) {
        this.Id_Notefrais = Id_Notefrais;
        this.Date_Notefrais = Date_Notefrais;
        this.DateSoumission_Notefrais = DateSoumission_Notefrais;
        this.Id_Utilisateur = Id_Utilisateur;
        this.Id_Client = Id_Client;
        this.lesDepenses = new ArrayList<Depense>();
    }

    public int getId_Notefrais() {
        return Id_Notefrais;
    }
    public void setId_Notefrais(int Id_Notefrais) {
        this.Id_Notefrais = Id_Notefrais;
    }
    public String getDate_Notefrais() {
        return Date_Notefrais;
    }
    public void setDate_Notefrais(String Date_Notefrais) {
        this.Date_Notefrais = Date_Notefrais;
    }
    public String getDateSoumission_Notefrais() {
        return DateSoumission_Notefrais;
    }
    public void setDateSoumission_Notefrais(String DateSoumission_Notefrais) {
        this.DateSoumission_Notefrais = DateSoumission_Notefrais;
    }
    public int getId_Utilisateur() {
        return Id_Utilisateur;
    }
    public void setId_Utilisateur(int Id_Utilisateur) {
        this.Id_Utilisateur = Id_Utilisateur;
    }
    public int getId_Client() {
        return Id_Client;
    }
    public void setId_Client(int Id_Client) {
        this.Id_Client = Id_Client;
    }
    public ArrayList<Depense> getLesDepenses() {
        return lesDepenses;
    }
    public void setLesDepenses(ArrayList<Depense> lesDepenses) {
        this.lesDepenses = lesDepenses;
    }
}
