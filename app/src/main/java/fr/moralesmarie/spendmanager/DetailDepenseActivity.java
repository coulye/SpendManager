package fr.moralesmarie.spendmanager;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import fr.moralesmarie.spendmanager.Class.Frais;
import fr.moralesmarie.spendmanager.Class.Trajet;
import fr.moralesmarie.spendmanager.HttpRequest.HttpGetRequest;

public class DetailDepenseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView titreDepense;

    private int idDepense;
    private String datePaiement;
    private String libelle;
    private String commentaire;
    private float montant;
    private int idNotefrais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail_depense);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        titreDepense = (TextView) findViewById(R.id.textTitreDetailDepense);
        TextView test = (TextView) findViewById(R.id.test);

        Intent intentDepense = getIntent();
        idDepense = Integer.parseInt(intentDepense.getStringExtra("id_depense"));
        datePaiement = intentDepense.getStringExtra("date_paiement");
        libelle = intentDepense.getStringExtra("libelle");
        commentaire = intentDepense.getStringExtra("commentaire");
        montant = Float.parseFloat(intentDepense.getStringExtra("montant"));
        idNotefrais = Integer.parseInt(intentDepense.getStringExtra("id_notefrais"));

        if (libelle.equals("Essence") || libelle.equals("Hotel") || libelle.equals("Parking") || libelle.equals("Restaurant")) {
            titreDepense.setText("Détails du Frais N° " + idDepense);
            Frais lefrais = catchFrais(idDepense);
            test.setVisibility(View.INVISIBLE);

        } else if (libelle.equals("Train") || libelle.equals("Taxi") || libelle.equals("Bus") || libelle.equals("Autoroute") || libelle.equals("Avion")) {
            titreDepense.setText("Détails du Trajet N° " + idDepense);
            Trajet leTrajet = catchTrajet(idDepense);
            test.setText(libelle);
        }
    }

    public Frais catchFrais(int idDepense){
        Frais leFrais = new Frais();

        String result = "";
        String myUrl = "http://moralesmarie.alwaysdata.net/public/depense/"+idDepense+"/frais";

        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            result = getRequest.execute(myUrl).get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            leFrais = new Frais(
                    jsonObject.getString("Date_Frais"),
                    idDepense,
                    datePaiement,
                    libelle,
                    commentaire,
                    montant,
                    idNotefrais
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return leFrais;
    }

    public Trajet catchTrajet(int idDepense){
        Trajet leTrajet = new Trajet();

        String result = "";
        String myUrl = "http://moralesmarie.alwaysdata.net/public/depense/"+idDepense+"/trajet";

        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            result = getRequest.execute(myUrl).get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(result);
            leTrajet = new Trajet(
                    jsonObject.getString("Duree_Trajet"),
                    jsonObject.getString("VilleDepart_Trajet"),
                    jsonObject.getString("VilleArrivee_Trajet"),
                    jsonObject.getString("DateAller_Trajet"),
                    jsonObject.getString("DateRetour_Trajet"),
                    Float.parseFloat(jsonObject.getString("Kilometre_Trajet")),
                    idDepense,
                    datePaiement,
                    libelle,
                    commentaire,
                    montant,
                    idNotefrais
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return leTrajet;
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
            Intent addNoteIntent = new Intent(DetailDepenseActivity.this, AddFraisActivity.class);
            startActivity(addNoteIntent);

        } else if (id == R.id.list_note) {
            Intent listNoteIntent = new Intent(DetailDepenseActivity.this, MenuActivity.class);
            startActivity(listNoteIntent);

        } else if (id == R.id.add_client) {

            Intent addClientIntent = new Intent(DetailDepenseActivity.this, AddClientActivity.class);
            startActivity(addClientIntent);

        } else if (id == R.id.view_stats) {
            Intent graphIntent = new Intent(DetailDepenseActivity.this, GraphActivity.class);
            startActivity(graphIntent);

        } else if (id == R.id.user_account) {

        } else if (id == R.id.user_deco) {
            Intent DecoIntent = new Intent(DetailDepenseActivity.this, LoginActivity.class);
            startActivity(DecoIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
