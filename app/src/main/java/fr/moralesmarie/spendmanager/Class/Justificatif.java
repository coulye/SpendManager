package fr.moralesmarie.spendmanager.Class;

/**
 * Created by utilisateur on 18/04/2018.
 */

public class Justificatif {
    private int Id_Justificatif;
    private String Titre_Justificatif;
    private String Url_Justificatif;
    private int Id_Depense;
    private int Id_Notefrais;

    public Justificatif(int Id_Justificatif, String Titre_Justificatif, String Url_Justificatif, int Id_Depense, int Id_Notefrais) {
        this.Id_Justificatif = Id_Justificatif;
        this.Titre_Justificatif = Titre_Justificatif;
        this.Url_Justificatif = Url_Justificatif;
        this.Id_Depense = Id_Depense;
        this.Id_Notefrais = Id_Notefrais;
    }

    public int getId_Justificatif() {
        return Id_Justificatif;
    }
    public void setId_Justificatif(int Id_Justificatif) {
        this.Id_Justificatif = Id_Justificatif;
    }
    public String getTitre_Justificatif() {
        return Titre_Justificatif;
    }
    public void setTitre_Justificatif(String Titre_Justificatif) {
        this.Titre_Justificatif = Titre_Justificatif;
    }
    public String getUrl_Justificatif() {
        return Url_Justificatif;
    }
    public void setUrl_Justificatif(String Url_Justificatif) {
        this.Url_Justificatif = Url_Justificatif;
    }
    public int getId_Depense() {
        return Id_Depense;
    }
    public void setId_Depense(int Id_Depense) {
        this.Id_Depense = Id_Depense;
    }
    public int getId_Notefrais() {
        return Id_Notefrais;
    }
    public void setId_Notefrais(int Id_Notefrais) {
        this.Id_Notefrais = Id_Notefrais;
    }
}
