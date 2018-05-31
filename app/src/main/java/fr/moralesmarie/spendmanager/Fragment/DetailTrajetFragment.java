package fr.moralesmarie.spendmanager.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.moralesmarie.spendmanager.R;

/**
 * Created by utilisateur on 30/05/2018.
 */

public class DetailTrajetFragment extends Fragment {

    private TextView textDuree;
    private String stringDuree;
    private TextView textVilleDepard;
    private String stringVilleDepard;
    private TextView textVilleArrivee;
    private String stringVilleArrivee;
    private TextView textDateAller;
    private String stringDateAller;
    private TextView textDateRetour;
    private String stringDateRetour;
    private TextView textKilometre;
    private String stringKilometre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_trajet_fragment, container, false);
        //Instancier vos composants graphique ici (faîtes vos findViewById)
        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textDuree = (TextView) view.findViewById(R.id.textDuree);
        textVilleDepard = (TextView) view.findViewById(R.id.textVilleDepard);
        textVilleArrivee = (TextView) view.findViewById(R.id.textVilleArrivee);
        textDateAller = (TextView) view.findViewById(R.id.textDateAller);
        textDateRetour = (TextView) view.findViewById(R.id.textDateRetour);
        textKilometre = (TextView) view.findViewById(R.id.textKilometre);

        textDuree.setText(stringDuree);
        textVilleDepard.setText(stringVilleDepard);
        textVilleArrivee.setText(stringVilleArrivee);
        textDateAller.setText(stringDateAller);
        textDateRetour.setText(stringDateRetour);
        textKilometre.setText(stringKilometre);
    }

    public void setStringDuree(String duree) {
        this.stringDuree = duree;
    }
    public void setStringVilleDepard(String villeDepard) {
        this.stringVilleDepard = villeDepard;
    }
    public void setStringVilleArrivee(String villeArrivee) {
        this.stringVilleArrivee = villeArrivee;
    }
    public void setStringDateAller(String dateAller) {
        this.stringDateAller = dateAller;
    }
    public void setStringDateRetour(String dateRetour) {
        this.stringDateRetour = dateRetour;
    }
    public void setStringKilometre(String kilometre) {
        this.stringKilometre = kilometre;
    }
}
