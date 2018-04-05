package fr.moralesmarie.spendmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddClientActivity extends AppCompatActivity {

    private EditText raisonSociale;
    private EditText adresse;
    private EditText cp;
    private EditText ville;
    private EditText mailEntreprise;
    private EditText telEntreprise;
    private RadioButton madame;
    private RadioButton monsieur;
    private EditText nom;
    private EditText prenom;
    private EditText mailInterlocuteur;
    private EditText telInterlocuteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        raisonSociale = (EditText) findViewById(R.id.textRaisonSociale);
        adresse = (EditText) findViewById(R.id.textAdresse);
        cp = (EditText) findViewById(R.id.textCodePostal);
        ville = (EditText) findViewById(R.id.textVille);
        mailEntreprise = (EditText) findViewById(R.id.textMailEntreprise);
        telEntreprise = (EditText) findViewById(R.id.textTelEntreprise);
        nom = (EditText) findViewById(R.id.textNom);
        prenom = (EditText) findViewById(R.id.textPrenom);
        mailInterlocuteur = (EditText) findViewById(R.id.textMailInterlocuteur);
        telInterlocuteur = (EditText) findViewById(R.id.textTelInterlocuteur);
        madame = (RadioButton) findViewById(R.id.radioMadame);
        monsieur = (RadioButton) findViewById(R.id.radioMonsieur);
    }
}
