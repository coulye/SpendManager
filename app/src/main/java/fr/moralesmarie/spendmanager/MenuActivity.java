package fr.moralesmarie.spendmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.moralesmarie.spendmanager.Class.Client;
import fr.moralesmarie.spendmanager.Class.Notefrais;
import fr.moralesmarie.spendmanager.HttpRequest.HttpGetRequest;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Spinner spinner;
    private static final String[]paths = {"tri par date", "tri par client"};

    private TableLayout table;
    private ArrayList<Notefrais> lesNotefrais;
    private ArrayList<Client> lesClients;
    private int idUtilisateur = 1;
    private ImageButton btnVoirDepense;
    private ImageButton btnAddFrais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        spinner = (Spinner)findViewById(R.id.spinner_liste_note);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MenuActivity.this,
                android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        lesNotefrais = listNotefrais(idUtilisateur);
        lesClients = listClients();
        LayoutInflater inflater = getLayoutInflater();
        table = (TableLayout) findViewById(R.id.tableLayout);
        for (Notefrais n : lesNotefrais) {
            final String idNotefrais = String.valueOf(n.getId_Notefrais());
            String leClient = "";
            for (Client c : lesClients) {
                if (c.getId_Client() == n.getId_Client()){
                    leClient = c.getNom_Client()+" "+c.getPrenom_Client();
                    break;
                }
            }
            TableRow tr = (TableRow) inflater.inflate(R.layout.tablerow_notefrais, null);
            ((TextView) tr.findViewById(R.id.textIdNotefrais)).setText("N° : "+idNotefrais);
            ((TextView) tr.findViewById(R.id.textDateNotefrais)).setText("Date : "+n.getDate_Notefrais());
            ((TextView) tr.findViewById(R.id.textNomClient)).setText("Client : "+leClient);
            tr.findViewById(R.id.btnVoirDepense).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentListDepense = new Intent(MenuActivity.this, ListDepenseActivity.class);
                    intentListDepense.putExtra("id_notefrais", idNotefrais);
                    startActivity(intentListDepense);
                }
            });
            table.addView(tr);
        }

        btnAddFrais = (ImageButton) findViewById(R.id.btnAddFrais);
        btnAddFrais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddFrais = new Intent(MenuActivity.this, AddFraisActivity.class);
                startActivity(intentAddFrais);
            }
        });
    }

    public ArrayList<Notefrais> listNotefrais(int idUtilisateur){
        ArrayList<Notefrais> lesNotefrais = new ArrayList<Notefrais>();
        String result = "";

        String myUrl = "http://moralesmarie.alwaysdata.net/public/utilisateur/"+idUtilisateur+"/notefrais";

        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            result = getRequest.execute(myUrl).get(); // exécution de la connexion
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONArray tblJSON = new JSONArray(result);
            for (int i = 0 ; i < tblJSON.length() ; i++) {
                JSONObject jsonObject = tblJSON.getJSONObject(i);
                Notefrais laNotefrais = new Notefrais(
                        jsonObject.getInt("Id_Notefrais"),
                        jsonObject.getString("Date_Notefrais"),
                        jsonObject.getString("DateSoumission_Notefrais"),
                        jsonObject.getInt("Id_Utilisateur"),
                        jsonObject.getInt("Id_Client")
                );
                lesNotefrais.add(laNotefrais);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lesNotefrais;
    }

    public ArrayList<Client> listClients(){
        ArrayList<Client> listClient = new ArrayList<Client>();
        String result = "";

//        String myUrl = "http://127.0.0.1:8080/REST-API-SY4/public/client";
        String myUrl = "http://moralesmarie.alwaysdata.net/public/client";

        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            result = getRequest.execute(myUrl).get(); // exécution de la connexion
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONArray tblJSON = new JSONArray(result);
            for (int i = 0 ; i < tblJSON.length() ; i++) {
                JSONObject jsonObject = tblJSON.getJSONObject(i);
                Client leClient = new Client(
                        jsonObject.getInt("Id_Client"),
                        jsonObject.getString("Titre_Client"),
                        jsonObject.getString("Nom_Client"),
                        jsonObject.getString("Prenom_Client"),
                        jsonObject.getString("Adresse_Client"),
                        jsonObject.getString("Cp_Client"),
                        jsonObject.getString("Ville_Client"),
                        jsonObject.getString("Telephone_Client"),
                        jsonObject.getString("Mail_Client"),
                        jsonObject.getString("Rs_Client")
                );
                listClient.add(leClient);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listClient;
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
            Intent addNoteIntent = new Intent(MenuActivity.this, AddFraisActivity.class);
            startActivity(addNoteIntent);

        } else if (id == R.id.list_note) {
            Intent listNoteIntent = new Intent(MenuActivity.this, MenuActivity.class);
            startActivity(listNoteIntent);

        } else if (id == R.id.add_client) {
            Intent addClientIntent = new Intent(MenuActivity.this, AddClientActivity.class);
            startActivity(addClientIntent);

        } else if (id == R.id.view_stats) {
            Intent graphIntent = new Intent(MenuActivity.this, GraphActivity.class);
            startActivity(graphIntent);

        } else if (id == R.id.user_account) {

        } else if (id == R.id.user_deco) {
            Intent DecoIntent = new Intent(MenuActivity.this, LoginActivity.class);
            startActivity(DecoIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
