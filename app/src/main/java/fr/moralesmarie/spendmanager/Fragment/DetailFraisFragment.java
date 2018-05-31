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

public class DetailFraisFragment extends Fragment {

    private TextView textDateFrais;
    private String stringDateFrais;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_frais_fragment, container, false);
        //Instancier vos composants graphique ici (faîtes vos findViewById)
        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textDateFrais = (TextView) view.findViewById(R.id.textDatefrais2);

        textDateFrais.setText(stringDateFrais);
    }

    public void setStringDateFrais(String dateFrais){
        this.stringDateFrais = dateFrais;
    }
}
