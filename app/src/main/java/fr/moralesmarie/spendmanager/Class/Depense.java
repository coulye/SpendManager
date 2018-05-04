package fr.moralesmarie.spendmanager.Class;

import java.util.ArrayList;

/**
 * Created by utilisateur on 18/04/2018.
 */

public class Depense {
    protected int Id_Depense;
    protected String DatePaiement_Depense;
    protected String Libelle_Depense;
    protected String Commentaire_Depense;
    protected float MontantRemboursement_Depense;
    protected int Id_Notefrais;
    //    protected NoteFrais laNotefrais;
    protected ArrayList<Justificatif> lesJustificatifs;

    public Depense() {
    }

    public Depense(int Id_Depense, String DatePaiement_Depense, String Libelle_Depense, String Commentaire_Depense, float MontantRemboursement_Depense, int Id_Notefrais) {
        this.Id_Depense = Id_Depense;
        this.DatePaiement_Depense = DatePaiement_Depense;
        this.Libelle_Depense = Libelle_Depense;
        this.Commentaire_Depense = Commentaire_Depense;
        this.MontantRemboursement_Depense = MontantRemboursement_Depense;
        this.Id_Notefrais = Id_Notefrais;
//        this.laNotefrais = null;
        this.lesJustificatifs = new ArrayList<Justificatif>();
    }

    public int getId_Depense() {
        return Id_Depense;
    }
    public void setId_Depense(int Id_Depense) {
        this.Id_Depense = Id_Depense;
    }
    public String getDatePaiement_Depense() {
        return DatePaiement_Depense;
    }
    public void setDatePaiement_Depense(String DatePaiement_Depense) {
        this.DatePaiement_Depense = DatePaiement_Depense;
    }
    public String getLibelle_Depense() {
        return Libelle_Depense;
    }
    public void setLibelle_Depense(String Libelle_Depense) {
        this.Libelle_Depense = Libelle_Depense;
    }
    public String getCommentaire_Depense() {
        return Commentaire_Depense;
    }
    public void setCommentaire_Depense(String Commentaire_Depense) {
        this.Commentaire_Depense = Commentaire_Depense;
    }
    public float getMontantRemboursement_Depense() {
        return MontantRemboursement_Depense;
    }
    public void setMontantRemboursement_Depense(float MontantRemboursement_Depense) {
        this.MontantRemboursement_Depense = MontantRemboursement_Depense;
    }
    public int getId_Notefrais() {
        return Id_Notefrais;
    }
    public void setId_Notefrais(int Id_Notefrais) {
        this.Id_Notefrais = Id_Notefrais;
    }
//    public NoteFrais getLaNotefrais() {
//        return laNotefrais;
//    }
//    public void setLaNotefrais(NoteFrais laNotefrais) {
//        this.laNotefrais = laNotefrais;
//    }
}
