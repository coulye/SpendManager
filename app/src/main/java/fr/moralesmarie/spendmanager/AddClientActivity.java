package fr.moralesmarie.spendmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import fr.moralesmarie.spendmanager.Class.Client;
import fr.moralesmarie.spendmanager.HttpRequest.HttpPostRequest;

public class AddClientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText raisonSociale;
    private EditText adresse;
    private EditText cp;
    private EditText ville;
    private EditText mail;
    private EditText tel;
    private RadioButton madame;
    private RadioButton monsieur;
    private EditText nom;
    private EditText prenom;

    private String titre = "";

    private Button btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add_client);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        madame = (RadioButton) findViewById(R.id.radioMadame);
        monsieur = (RadioButton) findViewById(R.id.radioMonsieur);
        nom = (EditText) findViewById(R.id.textNom);
        prenom = (EditText) findViewById(R.id.textPrenom);
        adresse = (EditText) findViewById(R.id.textAdresse);
        cp = (EditText) findViewById(R.id.textCodePostal);
        ville = (EditText) findViewById(R.id.textVille);
        tel = (EditText) findViewById(R.id.textTelEntreprise);
        mail = (EditText) findViewById(R.id.textMailEntreprise);
        raisonSociale = (EditText) findViewById(R.id.textRaisonSociale);

        btnValider = (Button) findViewById(R.id.btnValider);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (madame.isChecked()){
                    titre = "Madame";
                }
                else if (monsieur.isChecked()){
                    titre = "Monsieur";
                }
                Client leClient = new Client(
                        0,
                        titre,
                        nom.getText().toString(),
                        prenom.getText().toString(),
                        adresse.getText().toString(),
                        cp.getText().toString(),
                        ville.getText().toString(),
                        tel.getText().toString(),
                        mail.getText().toString(),
                        raisonSociale.getText().toString()
                );
                String result = addClient(leClient);
                if (result.equals("true\r")){
                    Context c = getApplicationContext();
                    Toast msg = Toast.makeText(c, "Le client a bien été ajouté !", Toast.LENGTH_SHORT);
                    msg.show();
                    Intent i = new Intent(AddClientActivity.this, AddFraisActivity.class);
                    startActivity(i);
                } else {
                    Context c = getApplicationContext();
                    Toast msg = Toast.makeText(c, "ERREUR : Echec de l'ajout du client !", Toast.LENGTH_SHORT);
                    msg.show();
                }
            }
        });
    }

    public String addClient(Client leClient){
        String result = "";

//        String myUrl = "http://172.20.10.5/REST-API-SY4/public/login.php";
        String myUrl = "http://127.0.0.1:8080/REST-API-SY4/public/client";
        String params =
                        "Titre_Client="+leClient.getTitre_Client()+
                        "&Nom_Client="+leClient.getNom_Client()+
                        "&Prenom_Client="+leClient.getPrenom_Client()+
                        "&Adresse_Client="+leClient.getAdresse_Client()+
                        "&Cp_Client="+leClient.getCp_Client()+
                        "&Ville_Client="+leClient.getVille_Client()+
                        "&Telephone_Client="+leClient.getTelephone_Client()+
                        "&Mail_Client="+leClient.getMail_Client()+
                        "&Rs_Client="+leClient.getRs_Client();

        HttpPostRequest postRequest = new HttpPostRequest();
        try{
            result = postRequest.execute(new String []{myUrl, params}).get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the MenuActivity; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add_note) {
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//            }
            Intent addNoteIntent = new Intent(AddClientActivity.this, AddFraisActivity.class);
            startActivity(addNoteIntent);

        } else if (id == R.id.list_note) {
            Intent listNoteIntent = new Intent(AddClientActivity.this, MenuActivity.class);

        } else if (id == R.id.add_client) {

            Intent addClientIntent = new Intent(AddClientActivity.this, AddClientActivity.class);
            startActivity(addClientIntent);

        } else if (id == R.id.view_stats) {

        } else if (id == R.id.user_account) {

        } else if (id == R.id.user_deco) {
            Intent DecoIntent = new Intent(AddClientActivity.this, LoginActivity.class);
            startActivity(DecoIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
