package fr.moralesmarie.spendmanager.Class;

/**
 * Created by utilisateur on 18/04/2018.
 */

public class Trajet extends Depense {
    private String Duree_Trajet;
    private String VilleDepart_Trajet;
    private String VilleArrivee_Trajet;
    private String DateAller_Trajet;
    private String DateRetour_Trajet;
    private float Kilometre_Trajet;

    public Trajet() {
        super();
    }

    public Trajet(String Duree_Trajet, String VilleDepart_Trajet, String VilleArrivee_Trajet, String DateAller_Trajet, String DateRetour_Trajet, float Kilometre_Trajet, int Id_Depense, String DatePaiement_Depense, String Libelle_Depense, String Commentaire_Depense, float MontantRemboursement_Depense, int Id_Notefrais) {
        super(Id_Depense, DatePaiement_Depense, Libelle_Depense, Commentaire_Depense, MontantRemboursement_Depense, Id_Notefrais);
        this.Duree_Trajet = Duree_Trajet;
        this.VilleDepart_Trajet = VilleDepart_Trajet;
        this.VilleArrivee_Trajet = VilleArrivee_Trajet;
        this.DateAller_Trajet = DateAller_Trajet;
        this.DateRetour_Trajet = DateRetour_Trajet;
        this.Kilometre_Trajet = Kilometre_Trajet;
    }

    public String getDuree_Trajet() {
        return Duree_Trajet;
    }
    public void setDuree_Trajet(String Duree_Trajet) {
        this.Duree_Trajet = Duree_Trajet;
    }
    public String getVilleDepart_Trajet() {
        return VilleDepart_Trajet;
    }
    public void setVilleDepart_Trajet(String VilleDepart_Trajet) {
        this.VilleDepart_Trajet = VilleDepart_Trajet;
    }
    public String getVilleArrivee_Trajet() {
        return VilleArrivee_Trajet;
    }
    public void setVilleArrivee_Trajet(String VilleArrivee_Trajet) {
        this.VilleArrivee_Trajet = VilleArrivee_Trajet;
    }
    public String getDateAller_Trajet() {
        return DateAller_Trajet;
    }
    public void setDateAller_Trajet(String DateAller_Trajet) {
        this.DateAller_Trajet = DateAller_Trajet;
    }
    public String getDateRetour_Trajet() {
        return DateRetour_Trajet;
    }
    public void setDateRetour_Trajet(String DateRetour_Trajet) {
        this.DateRetour_Trajet = DateRetour_Trajet;
    }
    public float getKilometre_Trajet() {
        return Kilometre_Trajet;
    }
    public void setKilometre_Trajet(float Kilometre_Trajet) {
        this.Kilometre_Trajet = Kilometre_Trajet;
    }
}
